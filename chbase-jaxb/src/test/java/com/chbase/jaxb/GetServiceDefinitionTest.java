package com.chbase.jaxb;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.chbase.ConnectionFactory;
import com.chbase.methods.jaxb.SimpleRequestTemplate;
import com.chbase.methods.jaxb.getservicedefinition2.request.GetServiceDefinition2Request;
import com.chbase.methods.jaxb.getservicedefinition2.response.GetServiceDefinition2Response;

import junit.framework.Assert;

@RunWith(JMock.class)
public class GetServiceDefinitionTest {

	private Mockery context = new JUnit4Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	/**
	 * Create the test case
	 *
	 */
	public GetServiceDefinitionTest() {
	}

	@Test
	public void GetServiceDefinition() throws Exception {
		SimpleRequestTemplate requestTemplate = new SimpleRequestTemplate(ConnectionFactory.getConnection());

		GetServiceDefinition2Response response = (GetServiceDefinition2Response) requestTemplate
				.makeRequest(new GetServiceDefinition2Request());

		Assert.assertNotNull(response);
	}
}
