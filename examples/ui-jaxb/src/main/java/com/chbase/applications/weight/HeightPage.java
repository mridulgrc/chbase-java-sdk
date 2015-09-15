package com.chbase.applications.weight;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chbase.applications.OfflineRequestTemplate;
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
import com.chbase.thing.oxm.jaxb.base.LengthValue;
import com.chbase.thing.oxm.jaxb.base.WeightValue;
import com.chbase.thing.oxm.jaxb.dates.DateTime;
import com.chbase.thing.oxm.jaxb.height.Height;
import com.chbase.thing.oxm.jaxb.thing.Thing2;
import com.chbase.thing.oxm.jaxb.weight.Weight;

public class HeightPage implements RequestHandler {
	/** The Constant WEIGHT_INPUT. */
	private static final String HEIGHT_INPUT = "height";
	
	/** The WEIGH t_ page. */
	private static String HEIGHT_PAGE = "/WEB-INF/height.jsp";

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
		//displayHeight(request, response);
		return HEIGHT_PAGE;
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
			String input = request.getParameter(HEIGHT_INPUT);
			String personid = request.getParameter("personid");
			String recordid = request.getParameter("recordid");
			if (input != null && input.trim().length() > 0)
			{	
		    	DisplayValue dv = new DisplayValue();
		    	dv.setUnits("lb");
		    	dv.setUnitsCode("lb");
		    	dv.setValue(Double.parseDouble(input));
		    	
		    	OfflineRequestTemplate heightReq = new OfflineRequestTemplate(personid, recordid); 
		    	PutThings2Request ptRequest = new PutThings2Request();
		    	LengthValue lv = new LengthValue();
		    	lv.setM(Double.parseDouble(input));
		    	lv.setDisplay(dv);
		    	Height height = new Height();
		    	height.setWhen(DateTime.fromCalendar(Calendar.getInstance()));
		    	height.setValue(lv);
		    	
		    	Thing2 thing = new Thing2();
		    	thing.setData(height);
		    	
		    	ptRequest.getThing().add(thing);
		    	heightReq.makeRequest(ptRequest);
			}
		}
	}

}
