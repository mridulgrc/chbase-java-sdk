package com.chbase.jaxb;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.chbase.ConnectionFactory;
import com.chbase.methods.jaxb.SimpleRequestTemplate;
import com.chbase.methods.jaxb.getupdatedrecordsforapplication2.request.GetUpdatedRecordsForApplication2Request;
import com.chbase.methods.jaxb.getupdatedrecordsforapplication2.response.GetUpdatedRecordsForApplication2Response;

@RunWith(JMock.class)
public class GetUpdatedRecordsForApplicationTest {

	private Mockery context = new JUnit4Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	/**
	 * Create the test case
	 *
	 */
	public GetUpdatedRecordsForApplicationTest() {
	}

	@Test
	@Ignore
	public void GetUpdatedRecords() throws Exception {
		SimpleRequestTemplate requestTemplate = new SimpleRequestTemplate(ConnectionFactory.getConnection());

		GetUpdatedRecordsForApplication2Response response = (GetUpdatedRecordsForApplication2Response) requestTemplate
				.makeRequest(new GetUpdatedRecordsForApplication2Request());

		Assert.assertNotNull(response);
	}
}
