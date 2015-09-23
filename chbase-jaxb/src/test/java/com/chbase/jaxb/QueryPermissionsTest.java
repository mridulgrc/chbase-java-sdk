package com.chbase.jaxb;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.chbase.methods.jaxb.SimpleRequestTemplate;
import com.chbase.methods.jaxb.querypermissions.request.QueryPermissionsRequest;
import com.chbase.methods.jaxb.querypermissions.response.QueryPermissionsResponse;

import junit.framework.Assert;

@RunWith(JMock.class)
public class QueryPermissionsTest {

	private Mockery context = new JUnit4Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	/**
	 * Create the test case
	 *
	 */
	public QueryPermissionsTest() {
	}

	@Test
	public void TestQueryPermissions() throws Exception {
		SimpleRequestTemplate requestTemplate = TestHelpers.GetRequestTemplate();

		QueryPermissionsRequest qp_req = new QueryPermissionsRequest();
		qp_req.getThingTypeId().add("3b3e6b16-eb69-483c-8d7e-dfe116ae6092");
		qp_req.getThingTypeId().add("3d34d87e-7fc1-4153-800f-f56592cb0d17");
		qp_req.getThingTypeId().add("40750a6a-89b2-455c-bd8d-b420a4cb500b");

		QueryPermissionsResponse qp_res = (QueryPermissionsResponse) requestTemplate.makeRequest(qp_req);

		Assert.assertNotNull(qp_res);
	}
}
