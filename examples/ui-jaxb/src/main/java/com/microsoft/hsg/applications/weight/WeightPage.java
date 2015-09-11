/*
testme * Copyright 2011 Microsoft Corp.
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
import com.microsoft.hsg.Request;
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
		//AddApplication();
		request.setAttribute("weights", things);
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
		Request request = new Request();
		request.setUserAuthToken(person_info.getUserAuthToken());
		request.setRecordId(person_info.getRecordId());
		thing2.addBlob("", fileInputStream, "image/png", request);

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
