/*
 * Copyright 2011 Microsoft Corp.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.microsoft.hsg.applications.weight;

import java.beans.Statement;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.DefaultEditorKit.InsertBreakAction;

import org.apache.commons.codec.binary.Base64;

import com.microsoft.hsg.ConnectionFactory;
import com.microsoft.hsg.applications.OfflineRequestTemplate;
import com.microsoft.hsg.applications.OnlineRequestTemplate;
import com.microsoft.hsg.applications.PersonInfo;
import com.microsoft.hsg.applications.RequestCtx;
import com.microsoft.hsg.applications.RequestHandler;
import com.microsoft.hsg.methods.jaxb.addapplication2.request.AddApplication2Request;
import com.microsoft.hsg.methods.jaxb.addapplication2.response.AddApplication2Response;
import com.microsoft.hsg.methods.jaxb.application.AppLargeLogoInfo;
import com.microsoft.hsg.methods.jaxb.application.CultureSpecificAppLargeLogo;
import com.microsoft.hsg.methods.jaxb.application.CultureSpecificStatement;
import com.microsoft.hsg.methods.jaxb.application.PublicKeys;
import com.microsoft.hsg.methods.jaxb.application.StatementInfo;
import com.microsoft.hsg.methods.jaxb.auth.Auth;
import com.microsoft.hsg.methods.jaxb.auth.AuthXml;
import com.microsoft.hsg.methods.jaxb.auth.Rule;
import com.microsoft.hsg.methods.jaxb.auth.Rules;
import com.microsoft.hsg.methods.jaxb.auth.Set;
import com.microsoft.hsg.methods.jaxb.beginputblob.request.BeginPutBlobRequest;
import com.microsoft.hsg.methods.jaxb.beginputblob.response.BeginPutBlobResponse;
import com.microsoft.hsg.methods.jaxb.getapplicationinfo2.request.GetApplicationInfo2Request;
import com.microsoft.hsg.methods.jaxb.getapplicationinfo2.response.GetApplicationInfo2Response;
import com.microsoft.hsg.methods.jaxb.getpersoninfo.request.GetPersonInfoRequest;
import com.microsoft.hsg.methods.jaxb.getpersoninfo.response.GetPersonInfoResponse;
import com.microsoft.hsg.methods.jaxb.getservicedefinition2.request.GetServiceDefinition2Request;
import com.microsoft.hsg.methods.jaxb.getservicedefinition2.response.GetServiceDefinition2Response;
import com.microsoft.hsg.methods.jaxb.getthings3.request.GetThings3Request;
import com.microsoft.hsg.methods.jaxb.getthings3.request.ThingFilterSpec;
import com.microsoft.hsg.methods.jaxb.getthings3.request.ThingFormatSpec2;
import com.microsoft.hsg.methods.jaxb.getthings3.request.ThingRequestGroup2;
import com.microsoft.hsg.methods.jaxb.getthings3.request.ThingSectionSpec2;
import com.microsoft.hsg.methods.jaxb.getthings3.response.GetThings3Response;
import com.microsoft.hsg.methods.jaxb.getthingtype.request.GetThingTypeRequest;
import com.microsoft.hsg.methods.jaxb.getthingtype.response.GetThingTypeResponse;
import com.microsoft.hsg.methods.jaxb.putthings2.request.PutThings2Request;
import com.microsoft.hsg.methods.jaxb.querypermissions.request.QueryPermissionsRequest;
import com.microsoft.hsg.methods.jaxb.querypermissions.response.QueryPermissionsResponse;
import com.microsoft.hsg.methods.jaxb.removeapplicationrecordauthorization.request.RemoveApplicationRecordAuthorizationRequest;
import com.microsoft.hsg.thing.oxm.jaxb.base.DisplayValue;
import com.microsoft.hsg.thing.oxm.jaxb.base.LengthValue;
import com.microsoft.hsg.thing.oxm.jaxb.base.WeightValue;
import com.microsoft.hsg.thing.oxm.jaxb.dates.Date;
import com.microsoft.hsg.thing.oxm.jaxb.dates.DateTime;
import com.microsoft.hsg.thing.oxm.jaxb.dates.Time;
import com.microsoft.hsg.thing.oxm.jaxb.height.Height;
import com.microsoft.hsg.thing.oxm.jaxb.personalimage.PersonalImage;
import com.microsoft.hsg.thing.oxm.jaxb.thing.BlobHashInfo;
import com.microsoft.hsg.thing.oxm.jaxb.thing.BlobHasher;
import com.microsoft.hsg.thing.oxm.jaxb.thing.BlobInfo;
import com.microsoft.hsg.thing.oxm.jaxb.thing.BlobPayload;
import com.microsoft.hsg.thing.oxm.jaxb.thing.BlobPayloadItem;
import com.microsoft.hsg.thing.oxm.jaxb.thing.Thing2;
import com.microsoft.hsg.thing.oxm.jaxb.thing.ThingType;
import com.microsoft.hsg.thing.oxm.jaxb.types.BlobHashAlgorithmParameters;
import com.microsoft.hsg.thing.oxm.jaxb.types.CultureSpecificString255;
import com.microsoft.hsg.thing.oxm.jaxb.types.CultureSpecificStringnz;
import com.microsoft.hsg.thing.oxm.jaxb.types.Permission;
import com.microsoft.hsg.thing.oxm.jaxb.weight.Weight;

/**
 * The Class WeightPage.
 */
public class WeightPage implements RequestHandler {

	/** The Constant WEIGHT_INPUT. */
	private static final String WEIGHT_INPUT = "weight";

	/** The WEIGH t_ page. */
	private static String WEIGHT_PAGE = "/WEB-INF/weight.jsp";

	/* (non-Javadoc)
	 * @see com.microsoft.hsg.applications.RequestHandler#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public String processRequest(HttpServletRequest request,
			HttpServletResponse response)
					throws Exception
	{
		if (request.getMethod().equalsIgnoreCase("post"))
		{
			processPost(request, response);
		}
		displayWeight(request, response);
		return WEIGHT_PAGE;
	}

	public boolean isAuthenticationRequired() {
		return true;
	}

	/**
	 * Process post.
	 * 
	 * @param request the request
	 * @param response the response
	 * 
	 * @throws Exception the exception
	 */
	/**
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void processPost(HttpServletRequest request, 
			HttpServletResponse response)
					throws Exception
	{
		if (request.getMethod().equalsIgnoreCase("post"))
		{
			String input = request.getParameter(WEIGHT_INPUT);
			if (input != null && input.trim().length() > 0)
			{	
				DisplayValue dv = new DisplayValue();
				dv.setUnits("lb");
				dv.setUnitsCode("lb");
				dv.setValue(Double.parseDouble(input));

				WeightValue wv = new WeightValue();
				wv.setKg(Double.parseDouble(input)/2.2);
				wv.setDisplay(dv);

				Weight weight = new Weight();
				weight.setWhen(DateTime.fromCalendar(Calendar.getInstance()));
				weight.setValue(wv);

				Thing2 thing = new Thing2();
				thing.setData(weight);

				PutThings2Request ptRequest = new PutThings2Request();
				ptRequest.getThing().add(thing);

				OnlineRequestTemplate requestTemplate = new OnlineRequestTemplate();
				Object obj = requestTemplate.makeRequest(ptRequest);

			}
		}
	}

	/**
	 * Display weight.
	 * 
	 * @param request the request
	 * @param response the response
	 * 
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void displayWeight(HttpServletRequest request, 
			HttpServletResponse response)
					throws Exception
	{
		OnlineRequestTemplate requestTemplate = new OnlineRequestTemplate();
		ThingRequestGroup2 group = new ThingRequestGroup2();

		ThingFilterSpec filter = new ThingFilterSpec();
		filter.getTypeId().add("3d34d87e-7fc1-4153-800f-f56592cb0d17");
		group.getFilter().add(filter);
		group.setMax(BigInteger.valueOf(30));

		ThingFormatSpec2 format = new ThingFormatSpec2();
		format.getSection().add(ThingSectionSpec2.CORE);
		format.getXml().add("");
		group.setFormat(format);    	

		GetThings3Request info = new GetThings3Request();
		info.getGroup().add(group);

		GetThings3Response gtResponse = 
				(GetThings3Response)requestTemplate.makeRequest(info);
		List things = gtResponse.getGroup().get(0).getThing();
		//_TestImage();
		_testMethods();
		//AddApplication();
		request.setAttribute("weights", things);
	}
	
	private void AddApplication() throws Exception
	{
		StatementInfo statement = new StatementInfo();
		statement.setContentType("text/base64");
		CultureSpecificStatement cul_state = new CultureSpecificStatement();
		cul_state.setLang("en-us");
		cul_state.setValue("hellow".getBytes());
		statement.getStatement().add(cul_state);
		
		OnlineRequestTemplate requestTemplate = new OnlineRequestTemplate();
		AddApplication2Request add_req = new AddApplication2Request();
		add_req.setActionUrl("http://hellow.com");
		add_req.setAppAuthRequired(true);
		add_req.setAppType("Web");
		add_req.setDomainName("hello.com");
		add_req.getName().add(new CultureSpecificString255("test", "en-us"));
		add_req.setDtcSuccessMessage(statement);
		add_req.setIsPublished(false);
		add_req.setPrivacyStatement(statement);
		add_req.setTermsOfUse(statement);
		add_req.getDescription().add(new CultureSpecificStringnz("desc"));
		add_req.getAuthReason().add(new CultureSpecificStringnz("hedws"));
		PublicKeys keys =  new PublicKeys();
		keys.getPublicKey().add("308201F730820160A003020102021061E156445E7454824D8C563EBB8E8653300D06092A864886F70D0101050500303A313830360603550403132F57696C646361744170702D66323339613839302D346334632D343839302D613564312D363461623635363761383438301E170D3135303432313135323833395A170D3339313233313030303030305A303A313830360603550403132F57696C646361744170702D66323339613839302D346334632D343839302D613564312D36346162363536376138343830819F300D06092A864886F70D010101050003818D0030818902818100BDA5B080BF555D9D2366A226B7FA0A5DAA77A0CCC54B5629D3D8626B807C05C3C1A90AE9B80C0BABE41CB7172DCAE9CBF4FE8F13FF79C2212475A71597D3457F115712F9B0345F18E8AACA51A0F56240E2A25CD89E4E6335DFC398BA2FED0436426E75CDABC428B43A7518833302B863687B321F041F72CF54BBD2C75C4CBA670203010001300D06092A864886F70D01010505000381810076F354F79F813195C2F716A75A2E5C1F2A049E47FD565D4577A0FCBB6AAC9D78DD9A7B4F36643F7B8B0FE79B10F3F1F6DE3C57766628B36D1252B69C13968F72C68C718A2B13AAB3EA44C7A3A7C356CBA07A7EC01490976D23C53931641EA35302ED585D81073FE36CDD33D6D0422CB62404DFB3F3F51005E38BFCB669BD729B");
		add_req.setPublicKeys(keys);
		AppLargeLogoInfo logo = new AppLargeLogoInfo();
		CultureSpecificAppLargeLogo cul_logo =  new CultureSpecificAppLargeLogo();
		cul_logo.setLang("en-US");
		cul_logo.setValue(GetFileBytes());
		logo.setContentType("image/png");
		logo.getLogo().add(0, cul_logo);
		add_req.setLargeLogo(logo);
		AuthXml auth_rule = new AuthXml();
		Auth auth = new Auth();
		Rule r = new Rule();
		r.setDisplayFlags((long)15);
		r.setIsOptional(false);
		r.setIsIncr(false);
		r.setName("pingu");
		r.getPermission().add(Permission.ALL);
		Set s = new Set();
		s.getTypeId().add("3b3e6b16-eb69-483c-8d7e-dfe116ae6092");
		r.getTargetSet().add(s);
		Rules rule = new Rules();
		rule.getRule().add(r);
		auth.setRules(rule);
		auth_rule.setAuth(auth);
		add_req.setPersonOnlineBaseAuth(auth_rule);
		add_req.setPersonOfflineBaseAuth(auth_rule);
		
		AddApplication2Response add_res = (AddApplication2Response)requestTemplate.makeRequest(add_req);
		
	}

	private void _testMethods() throws Exception 
	{
		OnlineRequestTemplate requestTemplate = new OnlineRequestTemplate();
		
		// Person Info
		GetPersonInfoRequest pers_req = new GetPersonInfoRequest();
		GetPersonInfoResponse pers_resp =(GetPersonInfoResponse )requestTemplate.makeRequest(pers_req);
		
		GetThingTypeRequest tt_req = new GetThingTypeRequest();
		tt_req.getId().add("3b3e6b16-eb69-483c-8d7e-dfe116ae6092");
		GetThingTypeResponse tt_res = (GetThingTypeResponse)requestTemplate.makeRequest(tt_req);
		
		//App info
		GetApplicationInfo2Request app_req = new GetApplicationInfo2Request();
		GetApplicationInfo2Response app_res = (GetApplicationInfo2Response )requestTemplate.makeRequest(app_req);
		
		//Svc dfn
		GetServiceDefinition2Request svc_req = new GetServiceDefinition2Request();
		GetServiceDefinition2Response svc_res = (GetServiceDefinition2Response)requestTemplate.makeRequest(svc_req);
		
		QueryPermissionsRequest qp_req =new QueryPermissionsRequest();
		qp_req.getThingTypeId().add("3b3e6b16-eb69-483c-8d7e-dfe116ae6092");
		qp_req.getThingTypeId().add("3d34d87e-7fc1-4153-800f-f56592cb0d17");
		qp_req.getThingTypeId().add("40750a6a-89b2-455c-bd8d-b420a4cb500b");
		
		QueryPermissionsResponse qp_res =(QueryPermissionsResponse)requestTemplate.makeRequest(qp_req);
		
		//RemoveApplicationRecordAuthorizationRequest remove_auth_req = new RemoveApplicationRecordAuthorizationRequest();
		//requestTemplate.makeRequest(remove_auth_req);
		
	}

	private void _TestImage() throws Exception 
	{
		OnlineRequestTemplate requestTemplate = new OnlineRequestTemplate();
		ThingRequestGroup2 group = new ThingRequestGroup2();

		ThingFilterSpec filter = new ThingFilterSpec();
		filter.getTypeId().add("a5294488-f865-4ce3-92fa-187cd3b58930");
		group.getFilter().add(filter);
		group.setMax(BigInteger.valueOf(30));

		ThingFormatSpec2 format = new ThingFormatSpec2();
		format.getSection().add(ThingSectionSpec2.CORE);
		format.getXml().add("");
		group.setFormat(format);    	

		GetThings3Request info = new GetThings3Request();
		info.getGroup().add(group);

		GetThings3Response gtResponse = 
				(GetThings3Response)requestTemplate.makeRequest(info);
		List things = gtResponse.getGroup().get(0).getThing();
		Thing2 thing2 = (Thing2)things.get(0);

		PersonInfo person_info = RequestCtx.getPersonInfo();


		byte[] file_bytes = GetFileBytes();
		try
		{
			thing2.getBlobPayload().getBlob().remove(0);
		}
		catch(Exception e)
		{

		}
		String exsistingFileName = "Z:\\image_16A9A542.png";
		File file = new File(exsistingFileName); 
		FileInputStream fileInputStream = new FileInputStream(file);
		//thing2.addBlob("", fileInputStream, "image/png",person_info.getPersonId(),person_info.getRecordId());

		//PutThings2Request ptRequest = new PutThings2Request();
		//ptRequest.getThing().add(thing2);
		//requestTemplate.makeRequest(ptRequest);
	}

	private byte[] GetFileBytes() throws IOException
	{
		String exsistingFileName = "C:\\Users\\rajeev\\Pictures\\image_16A9A542.png";
		File file = new File(exsistingFileName); 
		FileInputStream fileInputStream = new FileInputStream(file);
		int bytesAvailable = fileInputStream.available();
		byte[] buffer = new byte[bytesAvailable];
		fileInputStream.read(buffer, 0, bytesAvailable);
		return buffer;
	}
}
