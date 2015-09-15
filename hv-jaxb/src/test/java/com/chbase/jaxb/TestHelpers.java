package com.chbase.jaxb;

import com.chbase.ApplicationConfig;
import com.chbase.ConnectionFactory;
import com.chbase.methods.jaxb.SimpleRequestTemplate;

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
