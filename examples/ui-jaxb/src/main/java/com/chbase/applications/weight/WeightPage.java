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
package com.chbase.applications.weight;

import java.io.Console;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chbase.applications.OnlineRequestTemplate;
import com.chbase.applications.RequestHandler;
import com.chbase.methods.jaxb.getauthorizedrecords.request.GetAuthorizedRecordsRequest;
import com.chbase.methods.jaxb.getauthorizedrecords.response.GetAuthorizedRecordsResponse;
import com.chbase.methods.jaxb.getthings3.request.GetThings3Request;
import com.chbase.methods.jaxb.getthings3.request.ThingFilterSpec;
import com.chbase.methods.jaxb.getthings3.request.ThingFormatSpec2;
import com.chbase.methods.jaxb.getthings3.request.ThingRequestGroup2;
import com.chbase.methods.jaxb.getthings3.request.ThingSectionSpec2;
import com.chbase.methods.jaxb.getthings3.response.GetThings3Response;
import com.chbase.methods.jaxb.putthings2.request.PutThings2Request;
import com.chbase.thing.oxm.jaxb.base.DisplayValue;
import com.chbase.thing.oxm.jaxb.base.WeightValue;
import com.chbase.thing.oxm.jaxb.dates.DateTime;
import com.chbase.thing.oxm.jaxb.status.Status;
import com.chbase.thing.oxm.jaxb.thing.Thing2;
import com.chbase.thing.oxm.jaxb.thing.TypeManager;
import com.chbase.thing.oxm.jaxb.weight.Weight;

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
		List<Thing2> things = gtResponse.getGroup().get(0).getThing();
		TestAllTypes();

		request.setAttribute("weights", things);
	}

	private void TestAllTypes() throws InstantiationException, IllegalAccessException 
	{
		String [] test = { "CA3C57F4-F4C1-4E15-BE67-0A3CAF5414ED","5D8419AF-90F0-4875-A370-0F881C18F6B3","DAD8BB47-9AD0-4F09-A020-0FF051D1D0F7","ADAF49AD-8E10-49F8-9783-174819E97051",
				"A5294488-F865-4CE3-92FA-187CD3B58930","BD0403C5-4AE2-4B0E-A8DB-1888678E4528","609319BF-35CC-40A4-B9D7-1B329679BAAA","CD3587B5-B6E1-4565-AB3B-1C3AD45EB04F","AEA2E8F2-11DD-4A7D-AB43-1D58764EBC19",
				"5FD15CB7-B717-4B1C-89E0-1DBCF7F815DD","4A04FCC8-19C1-4D59-A8C7-2031A03F21DE","DD710B31-2B6F-45BD-9552-253562B9A7C1","DF4DB479-A1BA-42A2-8714-2B083B88150F","4B7971D6-E427-427D-BF2C-2FBCF76606B3",
				"9366440C-EC81-4B89-B231-308A4C4D70ED","85A21DDB-DB20-4C65-8D30-33C899CCF612","B81EB4A6-6EAC-4292-AE93-3872D6870994","089646A6-7E25-4495-AD15-3E28D4C1A71D","EF9CF8D5-6C0B-4292-997F-4047240BC7BE",
				"046D0AD7-6D7F-4BFD-AFD4-4192CA2E913D","356FBBA9-E0C9-4F4F-B0D9-4594F2490D2F","E75FA095-31ED-4B30-B5F7-463963B5E734","58FD8AC4-6C47-41A3-94B2-478401F0E26C","167ECF6B-BB54-43F9-A473-507B334907E0",
				"03EFE378-976A-42F8-AE1E-507C497A8C6D","7B2EA78C-4B78-4F75-A6A7-5396FE38B09A","FF9CE191-2096-47D8-9300-5469A9883746","52BF9104-2C5E-4F1F-A66D-552EBCC53DF7","A9A76456-0357-493E-B840-598BBB9483FD",
				"5E2C027E-3417-4CFC-BD10-5A6F2E91AD23","30CAFCCC-047D-4288-94EF-643571F7919D","25C94A9F-9D3D-4576-96DC-6791178A8143","73822612-C15F-4B49-9E65-6AF369E55C65","92BA621E-66B3-4A01-BD73-74844AED4F5B",
				"9AD2A94F-C6A4-4D78-8B50-75B65BE0E250","7AB3E662-CC5B-4BE2-BF38-78F8AAD5B161","9085CAD9-E866-4564-8A91-7AD8685D204D","031F5706-7F1A-11DB-AD56-7BD355D89593","D4B48E6B-50FA-4BA8-AC73-7D64A68DC328",
				"6705549B-0E3D-474E-BFA7-8197DDD6786A","55D33791-58DE-4CAE-8C78-819E12BA5059","46D485CF-2B84-429D-9159-83152BA801F4","98F76958-E34F-459B-A760-83C1699ADD38","D3170D30-A41B-4BDE-A116-87698C8A001A",
				"11C52484-7F1A-11DB-AEAC-87D355D89593","C9287326-BB43-4194-858C-8B60768F000F","822A5E5A-14F1-4D06-B92F-8F3F1B05218F","9F4E0FCD-10D7-416D-855A-90514CE2016B","80CF4080-AD3F-4BB5-A0B5-907C22F73017",				
				"162DD12D-9859-4A66-B75F-96760D67072B","E4911BD3-61BF-4E10-AE78-9C574B888B8F","1572AF76-1653-4C39-9683-9F9CA6584BA3","02EF57A2-A620-425A-8E92-A301542CCA54","21D75546-8717-4DEB-8B17-A57F48917790",
				"62160199-B80F-4905-A55A-AC4BA825CEAE","879E7C04-4E8A-4707-9AD3-B054DF467CE4","40750A6A-89B2-455C-BD8D-B420A4CB500B","CC23422C-4FBA-4A23-B52A-C01D6CD53FDF","184166BE-8ADB-4D9C-8162-C403040E31AD",
				"D33A32B2-00DE-43B8-9F2A-C4C7E9F580EC","B7925180-D69E-48FA-AE1D-CB3748CA170E","A5033C9D-08CF-4204-9BD3-CB412CE39FC0","66AC44C7-1D60-4E95-BB5B-D21490E91057","3B3C053B-B1FE-4E11-9E22-D4B480DE74E8",
				"E4501363-FB95-4A11-BB60-DA64E98048B5","464083CC-13DE-4F3E-A189-DA8E47D5651B","4B18AEB6-5F01-444C-8C70-DBF13A2F510B","3B3E6B16-EB69-483C-8D7E-DFE116AE6092","B8FCB138-F8E6-436A-A15D-E3A2D6916094",
				"18ADC276-5144-4E7E-BF6C-E56D8250ADF8","7EA47715-CBA4-47F0-99D2-EB0A9FB4A85C","72DC49E1-1486-4634-B651-EF560ED051E5","5800EAB5-A8C2-482A-A4D6-F1DB25AE08C3",
				"D65AD514-C492-4B59-BD05-F3F6CB43CEB3","3D34D87E-7FC1-4153-800F-F56592CB0D17","7EA7A1F9-880B-4BD4-B593-F5660F20EDA8","3A54F95F-03D8-4F62-815F-F691FC94A500",
				"9C29C6B9-F40E-44FF-B24E-FBA6F3074638"};
		for(int i=0;i<78;i++)
		{
			System.out.println(test[i]);
			Object a = TypeManager.getClassForType(test[i]).newInstance();
		}
		
		
	}
}
