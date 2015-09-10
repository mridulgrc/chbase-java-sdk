package com.microsoft.hsg.applications.weight;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.microsoft.hsg.applications.RequestHandler;

public class ImageUploadPage implements RequestHandler 
{
	private static String PROFILE_PIC_PAGE = "/WEB-INF/profile_pic.jsp";
	public String processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (request.getMethod().equalsIgnoreCase("post"))
		{
			processPost(request, response);
		}
		return PROFILE_PIC_PAGE;
	}

	private void processPost(HttpServletRequest request, HttpServletResponse response) 
	{
		// TODO Auto-generated method stub
		
	}

	public boolean isAuthenticationRequired() {
		// TODO Auto-generated method stub
		return false;
	}

}
