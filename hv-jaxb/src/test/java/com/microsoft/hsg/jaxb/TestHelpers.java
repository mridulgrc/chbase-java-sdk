package com.microsoft.hsg.jaxb;

import com.microsoft.hsg.ApplicationConfig;
import com.microsoft.hsg.ConnectionFactory;
import com.microsoft.hsg.methods.jaxb.SimpleRequestTemplate;

public class TestHelpers 
{

	public static SimpleRequestTemplate GetRequestTemplate()
	{
		SimpleRequestTemplate requestTemplate = new SimpleRequestTemplate(
				ConnectionFactory.getConnection());
		requestTemplate.setPersonId(ApplicationConfig.Test_PersonID);
		requestTemplate.setRecordId(ApplicationConfig.Test_RecordID);
		return requestTemplate;
	}

}
