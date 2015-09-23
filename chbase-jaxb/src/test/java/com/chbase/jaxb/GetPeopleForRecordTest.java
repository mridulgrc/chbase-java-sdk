package com.chbase.jaxb;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.chbase.methods.jaxb.SimpleRequestTemplate;
import com.chbase.methods.jaxb.getpeopleforrecord.request.GetPeopleForRecordRequest;
import com.chbase.methods.jaxb.getpeopleforrecord.response.GetPeopleForRecordResponse;

import junit.framework.Assert;

@RunWith(JMock.class)
public class GetPeopleForRecordTest {

	private Mockery context = new JUnit4Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	/**
	 * Create the test case
	 *
	 */
	public GetPeopleForRecordTest() {
	}

	@Test
	public void TestGetPeopleForRecord() throws Exception {
		SimpleRequestTemplate requestTemplate = TestHelpers.GetRequestTemplate();

		GetPeopleForRecordRequest req = new GetPeopleForRecordRequest();
		GetPeopleForRecordResponse res = (GetPeopleForRecordResponse) requestTemplate.makeRequest(req);

		Assert.assertNotNull(res);
	}
}
