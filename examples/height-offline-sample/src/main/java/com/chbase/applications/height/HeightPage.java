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
package com.chbase.applications.height;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chbase.ApplicationConfig;
import com.chbase.applications.OfflineRequestTemplate;
import com.chbase.applications.RequestHandler;
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
import com.chbase.thing.oxm.jaxb.height.Height;
import com.chbase.thing.oxm.jaxb.thing.Thing2;
import com.chbase.thing.oxm.jaxb.weight.Weight;

/**
 * The Class WeightPage.
 */
public class HeightPage implements RequestHandler {

	private static final String PERSONID_INPUT = "personid";
	private static final String RECORDID_INPUT = "recordid";

	/** The WEIGH t_ page. */
	private static String HEIGHT_PAGE = "/WEB-INF/height.jsp";

	/* (non-Javadoc)
	 * @see com.microsoft.hsg.applications.RequestHandler#processRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public String processRequest(HttpServletRequest request,
			HttpServletResponse response)
					throws Exception
	{
		request.setAttribute("heights", new ArrayList());
		if (request.getMethod().equalsIgnoreCase("post"))
		{
			processPost(request, response);
		}
		return HEIGHT_PAGE;
	}

	public boolean isAuthenticationRequired() {
		return false;
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
			String personid = request.getParameter(PERSONID_INPUT);
			String recordid = request.getParameter(RECORDID_INPUT);
			if (personid  != null && personid .trim().length() > 0)
			{	
				displayHeight(request, response, personid, recordid);
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
	private void displayHeight(HttpServletRequest request, 
			HttpServletResponse response, String personid, String recordid)
					throws Exception
	{
		OfflineRequestTemplate requestTemplate = new OfflineRequestTemplate(personid,recordid);
		ThingRequestGroup2 group = new ThingRequestGroup2();

		ThingFilterSpec filter = new ThingFilterSpec();
		filter.getTypeId().add("40750a6a-89b2-455c-bd8d-b420a4cb500b");
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

		request.setAttribute("heights", things);
	}
}
