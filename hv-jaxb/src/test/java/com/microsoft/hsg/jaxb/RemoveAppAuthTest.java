package com.microsoft.hsg.jaxb;

import junit.framework.Assert;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.microsoft.hsg.ConnectionFactory;
import com.microsoft.hsg.methods.jaxb.SimpleRequestTemplate;
import com.microsoft.hsg.methods.jaxb.querypermissions.request.QueryPermissionsRequest;
import com.microsoft.hsg.methods.jaxb.querypermissions.response.QueryPermissionsResponse;
import com.microsoft.hsg.methods.jaxb.removeapplicationrecordauthorization.request.RemoveApplicationRecordAuthorizationRequest;


@RunWith(JMock.class)
public class RemoveAppAuthTest {


	private Mockery context = new JUnit4Mockery() {{
		setImposteriser(ClassImposteriser.INSTANCE);
	}};

	/**
	 * Create the test case
	 *
	 */
	public RemoveAppAuthTest()
	{
	}

	@Test
	@Ignore
	public void TestRemoveAppAuth() throws Exception
	{
		SimpleRequestTemplate requestTemplate = TestHelpers.GetRequestTemplate();

		RemoveApplicationRecordAuthorizationRequest remove_auth_req = new RemoveApplicationRecordAuthorizationRequest();
		requestTemplate.makeRequest(remove_auth_req);

		Assert.assertNotNull(1);
	}
}
