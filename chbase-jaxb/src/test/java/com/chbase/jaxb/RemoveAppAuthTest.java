package com.chbase.jaxb;

import junit.framework.Assert;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.chbase.ConnectionFactory;
import com.chbase.methods.jaxb.SimpleRequestTemplate;
import com.chbase.methods.jaxb.querypermissions.request.QueryPermissionsRequest;
import com.chbase.methods.jaxb.querypermissions.response.QueryPermissionsResponse;
import com.chbase.methods.jaxb.removeapplicationrecordauthorization.request.RemoveApplicationRecordAuthorizationRequest;

@RunWith(JMock.class)
public class RemoveAppAuthTest {

	private Mockery context = new JUnit4Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	/**
	 * Create the test case
	 *
	 */
	public RemoveAppAuthTest() {
	}

	@Test
	@Ignore
	public void TestRemoveAppAuth() throws Exception {
		SimpleRequestTemplate requestTemplate = TestHelpers.GetRequestTemplate();

		RemoveApplicationRecordAuthorizationRequest remove_auth_req = new RemoveApplicationRecordAuthorizationRequest();
		requestTemplate.makeRequest(remove_auth_req);

		Assert.assertNotNull(1);
	}
}
