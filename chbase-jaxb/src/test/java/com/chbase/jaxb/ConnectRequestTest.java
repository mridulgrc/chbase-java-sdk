package com.chbase.jaxb;

import java.util.Random;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.chbase.ConnectionFactory;
import com.chbase.methods.jaxb.SimpleRequestTemplate;
import com.chbase.methods.jaxb.createconnectrequest.request.CreateConnectRequestRequest;
import com.chbase.methods.jaxb.createconnectrequest.response.CreateConnectRequestResponse;
import com.chbase.methods.jaxb.deletependingconnectrequest.request.DeletePendingConnectRequestRequest;
import com.chbase.methods.jaxb.updateexternalid.request.UpdateExternalIdRequest;

import junit.framework.Assert;

@RunWith(JMock.class)
public class ConnectRequestTest {

	private Mockery context = new JUnit4Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	/**
	 * Create the test case
	 *
	 */
	public ConnectRequestTest() {
	}

	@Test
	public void CreateConnectRequest() throws Exception {
		SimpleRequestTemplate requestTemplate = new SimpleRequestTemplate(ConnectionFactory.getConnection());

		CreateConnectRequestRequest connectRequest = new CreateConnectRequestRequest();
		connectRequest.setFriendlyName("friendly-name");
		connectRequest.setQuestion("What is your favorite color");
		connectRequest.setAnswer("green");
		String externalid = newExternalId();
		connectRequest.setExternalId(externalid);

		CreateConnectRequestResponse response = (CreateConnectRequestResponse) requestTemplate
				.makeRequest(connectRequest);
		Assert.assertNotNull(response);

		externalid = UpdateExternalIDTest(externalid, response.getIdentityCode());
		DeletePendingConnectionrequestsTest(externalid, response.getIdentityCode());
	}

	private String UpdateExternalIDTest(String oldExtid, String identity) throws Exception {
		SimpleRequestTemplate requestTemplate = new SimpleRequestTemplate(ConnectionFactory.getConnection());
		String ext_id = newExternalId();
		UpdateExternalIdRequest req = new UpdateExternalIdRequest();
		req.setIdentityCode(identity);
		req.setNewExternalId(ext_id);
		requestTemplate.makeRequest(req);
		return ext_id;
	}

	private void DeletePendingConnectionrequestsTest(String extid, String identity) throws Exception {
		DeletePendingConnectRequestRequest req = new DeletePendingConnectRequestRequest();
		req.setExternalId(extid);
		SimpleRequestTemplate requestTemplate = new SimpleRequestTemplate(ConnectionFactory.getConnection());
		requestTemplate.makeRequest(req);
	}

	private String newExternalId() throws Exception {
		byte[] buf = new byte[20];

		Random rand = new Random();
		rand.nextBytes(buf);

		return hexToString(buf);
	}

	static final byte[] HEX_CHARS = { (byte) '0', (byte) '1', (byte) '2', (byte) '3', (byte) '4', (byte) '5',
			(byte) '6', (byte) '7', (byte) '8', (byte) '9', (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd', (byte) 'e',
			(byte) 'f' };

	private static String hexToString(byte[] raw) throws Exception {
		byte[] hex = new byte[2 * raw.length];
		int index = 0;

		for (byte b : raw) {
			int v = b & 0xFF;
			hex[index++] = HEX_CHARS[v >>> 4];
			hex[index++] = HEX_CHARS[v & 0xF];
		}
		return new String(hex, "ASCII");
	}

}
