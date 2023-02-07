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
package com.chbase.applications.referral;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chbase.applications.OnlineRequestTemplate;
import com.chbase.applications.RequestHandler;
import com.chbase.methods.jaxb.getthings3.request.GetThings3Request;
import com.chbase.methods.jaxb.getthings3.request.ThingFilterSpec;
import com.chbase.methods.jaxb.getthings3.request.ThingFormatSpec2;
import com.chbase.methods.jaxb.getthings3.request.ThingRequestGroup2;
import com.chbase.methods.jaxb.getthings3.request.ThingSectionSpec2;
import com.chbase.methods.jaxb.getthings3.response.GetThings3Response;
import com.chbase.methods.jaxb.putthings2.request.PutThings2Request;
import com.chbase.thing.oxm.jaxb.base.DisplayValue;
import com.chbase.thing.oxm.jaxb.dates.DateTime;
import com.chbase.thing.oxm.jaxb.thing.Thing2;
import com.chbase.thing.oxm.jaxb.referral.Referral;
import com.chbase.thing.oxm.jaxb.base.CodableValue;
import com.chbase.thing.oxm.jaxb.base.CodedValue;
import com.chbase.thing.oxm.jaxb.base.Task;
import java.util.ArrayList;
import com.chbase.thing.oxm.jaxb.thing.Common;
import com.chbase.thing.oxm.jaxb.base.Person;
import com.chbase.thing.oxm.jaxb.dates.DateTime;
import com.chbase.thing.oxm.jaxb.base.Name;

/**
 * The Class ReferralPage.
 */
public class ReferralPage implements RequestHandler {

	/** The Constant REFERRAL_INPUT. */
	private static final String REFERRAL_INPUT = "referral";

	/** The Constant CLIENTID_INPUT. */
	private static final String CLIENTID_INPUT = "clientId";

	/** The WEIGH t_ page. */
	private static String REFERRAL_PAGE = "/WEB-INF/referral.jsp";

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
		displayReferral(request, response);
		return REFERRAL_PAGE;
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
			String input = request.getParameter(REFERRAL_INPUT);
			String clientIdInput = request.getParameter(CLIENTID_INPUT);
			if (input != null && input.trim().length() > 0)
			{	

				
				Referral referral = new Referral();
				CodableValue reason = new CodableValue(input, new CodedValue("value", "family", "type", "version"));
				referral.setReason(reason);
				
				Thing2 thing = new Thing2();
				thing.setData(referral);
				
				if (clientIdInput != null && clientIdInput.trim().length() > 0){
					Common common = new Common();
					common.setClientThingId(clientIdInput);						
					thing.getDataXml().get(0).setCommon(common);
				}

				PutThings2Request ptRequest = new PutThings2Request();
				ptRequest.getThing().add(thing);

				OnlineRequestTemplate requestTemplate = new OnlineRequestTemplate();
				Object obj = requestTemplate.makeRequest(ptRequest);


			}
		}
	}

	/**
	 * Display Referral.
	 * 
	 * @param request the request
	 * @param response the response
	 * 
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void displayReferral(HttpServletRequest request, 
			HttpServletResponse response)
					throws Exception
	{
		OnlineRequestTemplate requestTemplate = new OnlineRequestTemplate();
		ThingRequestGroup2 group = new ThingRequestGroup2();

		ThingFilterSpec filter = new ThingFilterSpec();
		filter.getTypeId().add("B861CB93-9CD3-408A-9C65-E5F58E4E2C30");
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

		request.setAttribute("referrals", things);
	}
}
