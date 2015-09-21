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
import com.chbase.methods.jaxb.allocatepackageid.request.AllocatePackageIdRequest;
import com.chbase.methods.jaxb.allocatepackageid.response.AllocatePackageIdResponse;

@RunWith(JMock.class)
public class AllocatePackageIdTest {

	private Mockery context = new JUnit4Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	/**
	 * Create the test case
	 *
	 */
	public AllocatePackageIdTest() {
	}

	@Ignore
	@Test
	public void AllocatePackageId() throws Exception {
		SimpleRequestTemplate requestTemplate = new SimpleRequestTemplate(ConnectionFactory.getConnection());

		AllocatePackageIdResponse response = (AllocatePackageIdResponse) requestTemplate
				.makeRequest(new AllocatePackageIdRequest());

		Assert.assertNotNull(response);
	}
}
