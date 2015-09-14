package com.microsoft.hsg.jaxb;

import junit.framework.Assert;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.microsoft.hsg.ApplicationConfig;
import com.microsoft.hsg.methods.jaxb.SimpleRequestTemplate;
import com.microsoft.hsg.methods.jaxb.getauthorizedrecords.request.GetAuthorizedRecordsRequest;
import com.microsoft.hsg.methods.jaxb.getauthorizedrecords.response.GetAuthorizedRecordsResponse;


@RunWith(JMock.class)
public class GetAuthorizedRecordsTest {


	private Mockery context = new JUnit4Mockery() {{
		setImposteriser(ClassImposteriser.INSTANCE);
	}};

	/**
	 * Create the test case
	 *
	 */
	public GetAuthorizedRecordsTest()
	{
	}

	@Test
	public void TestGetAuthorizedRecords() throws Exception
	{
		SimpleRequestTemplate requestTemplate = TestHelpers.GetRequestTemplate();

		GetAuthorizedRecordsRequest req = new GetAuthorizedRecordsRequest();
		req.getId().add(ApplicationConfig.Test_PersonID);
		GetAuthorizedRecordsResponse resp = (GetAuthorizedRecordsResponse)requestTemplate.makeRequest(req);
		Assert.assertNotNull(resp);
	}
}