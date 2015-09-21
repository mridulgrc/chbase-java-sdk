package com.chbase.jaxb;

import junit.framework.Assert;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.chbase.ConnectionFactory;
import com.chbase.methods.jaxb.SimpleRequestTemplate;
import com.chbase.methods.jaxb.getpersoninfo.request.GetPersonInfoRequest;
import com.chbase.methods.jaxb.getpersoninfo.response.GetPersonInfoResponse;

@RunWith(JMock.class)
public class GetPersonInfoTest {

	private Mockery context = new JUnit4Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	/**
	 * Create the test case
	 *
	 */
	public GetPersonInfoTest() {
	}

	@Test
	public void GetPersonInfo() throws Exception {
		SimpleRequestTemplate requestTemplate = TestHelpers.GetRequestTemplate();

		GetPersonInfoResponse response = (GetPersonInfoResponse) requestTemplate
				.makeRequest(new GetPersonInfoRequest());

		Assert.assertNotNull(response);
	}
}
