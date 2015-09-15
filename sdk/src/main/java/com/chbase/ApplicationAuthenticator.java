/*
 * Copyright 2011 Microsoft Corp.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.chbase;

import java.security.PrivateKey;
import java.security.Signature;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;


/**
 * ApplicationAuthenticator authenticates a connection
 * for an application with a certificate.  When the connection
 * requests it, retrieve an authenticated session token from
 * HealthVault. 
 */
public class ApplicationAuthenticator implements Authenticator
{
	private String appId;
	private SharedSecretGenerator sharedSecretGenerator;
	private byte[] sharedSecret;
	private String sessionToken;
	private PrivateKey privateKey;
	private String thumbprint;
	private PrivateKeyStore keyStore;
	private HVAccessor accessor;
	
	/**
	 * Instantiates a new application authenticator.
	 */
	public ApplicationAuthenticator()
	{
		accessor = new HVAccessor();
		accessor.setSendStrategy(new SimpleSendStrategy());
	}
	
	/**
	 * Gets the session token.
	 * 
	 * @return the session token
	 */
	public String getSessionToken()
	{
		return sessionToken;
	}

	/**
	 * Gets the shared secret generator.
	 * 
	 * @return the shared secret generator
	 */
	public SharedSecretGenerator getSharedSecretGenerator()
	{
		return sharedSecretGenerator;
	}

	/**
	 * Sets the shared secret generator.
	 * 
	 * @param sharedSecretGenerator the new shared secret generator
	 */
	public void setSharedSecretGenerator(SharedSecretGenerator sharedSecretGenerator)
	{
		this.sharedSecretGenerator = sharedSecretGenerator;
	}

	/**
	 * Gets the app id.
	 * 
	 * @return the app id
	 */
	public String getAppId()
	{
		return appId;
	}

	/**
	 * Sets the app id.
	 * 
	 * @param appId the new app id
	 */
	public void setAppId(String appId)
	{
		this.appId = appId;
	}
	/**
	 * Gets the key store.
	 * 
	 * @return the key store
	 */
	public PrivateKeyStore getKeyStore()
	{
		return keyStore;
	}

	/**
	 * Sets the key store.
	 * 
	 * @param keyStore the new key store
	 */
	public void setKeyStore(PrivateKeyStore keyStore)
	{
		this.keyStore = keyStore;
	}

	/* (non-Javadoc)
	 * @see com.microsoft.hsg.Authenticator#authenticate(com.microsoft.hsg.Connection, boolean)
	 */
	public synchronized void authenticate(Connection connection, boolean force)
	{
		connection.setAppId(appId);
		
		if( !force &&
			sharedSecret != null &&
			sessionToken != null  )
		{
			connection.setSharedSecret(sharedSecret);
			connection.setSessionToken(sessionToken);
		}
		else
		{
			authenticate(connection);
		}
	}
	
	private  void authenticate(Connection connection)
	{
	
		ensurePrivateKey();
		
		connection.setSessionToken(null);
		Request castRequest = createAuthenticatedSessionTokenRequest();
		accessor.send(castRequest, connection);
		Response response = accessor.getResponse();
		
		try
		{
			InputSource src = new InputSource(response.getInputStream());
			DocumentBuilderFactory doc_factory =  DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = doc_factory.newDocumentBuilder();
			Document doc = builder.parse(src);
	        XPath xpath = XPathFactory.newInstance().newXPath();
	        String exp = "//token";
	        sessionToken = xpath.evaluate(exp,doc);
	        exp = "//shared-secret";
	        sharedSecret=Base64.decodeBase64( xpath.evaluate(exp, doc).getBytes());	        
		}
		catch(Exception e)
		{
			throw new HVSystemException("XPath failed to get token", e);
		}
		
        if (sessionToken == null)
        {
        	throw new HVSystemException( "Application session token not found");
        }
        
        connection.setSessionToken(sessionToken);
        connection.setSharedSecret(sharedSecret);
	
    }

	private Request createAuthenticatedSessionTokenRequest() 
    {
		try 
		{
			Date date = new Date();
			String sign_date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S").format(date);
	        StringBuilder contentBuilder = new StringBuilder();
	        contentBuilder.append("<content><app-id>");
	        contentBuilder.append(appId);
	        contentBuilder.append("</app-id><hmac>HMACSHA256</hmac>");
	        contentBuilder.append("<signing-time>"+ sign_date +"</signing-time>");
	        contentBuilder.append("</content>");
	        String content = contentBuilder.toString();
	          
	        StringBuilder infoBuilder = new StringBuilder();
	        infoBuilder.append("<info><auth-info><app-id>");
	        infoBuilder.append(appId);
	        infoBuilder.append("</app-id><credential><appserver2><sig digestMethod=\"SHA1\" sigMethod=\"RSA-SHA1\" thumbprint=\"");
	        infoBuilder.append(thumbprint);
	        infoBuilder.append("\">");
	        infoBuilder.append(new String(Base64.encodeBase64(signContent(content.getBytes("UTF-8")))));
	        infoBuilder.append("</sig>");
	        infoBuilder.append(content);
	        infoBuilder.append("</appserver2></credential></auth-info></info>");
	        
	        //TODO: create CASTRequest extends Request to include specific details
	    	Request castRequest = new Request();
	    	castRequest.setMethodName("CreateAuthenticatedSessionToken");
	    	castRequest.setMethodVersion("2");
	    	castRequest.setInfo(infoBuilder.toString());
	    	
	    	return castRequest;
		}
		catch(Exception e)
		{
			throw new HVSystemException("Could not build CAST request", e);
		}
    }
	
	private byte[] signContent(byte[] content) 
    {
		try
		{
	        Signature dsa = Signature.getInstance("SHA1withRSA");
	        dsa.initSign(privateKey);
	        dsa.update(content);
	        return dsa.sign();
		} catch (Exception e)
		{
			throw new HVSystemException("Could not sign request", e);
		}
    }
	
    private void ensurePrivateKey()
    {
    	if (keyStore == null)
    	{
    		throw new HVSystemException("KeyStore not set on Authenticator");
    	}
    	
    	privateKey = keyStore.getPrivateKey();
    	thumbprint = keyStore.getThumbprint();
    }
}
