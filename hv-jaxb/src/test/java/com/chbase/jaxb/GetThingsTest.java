package com.chbase.jaxb;

import java.math.BigInteger;

import junit.framework.Assert;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.chbase.ConnectionFactory;
import com.chbase.Request;
import com.chbase.methods.jaxb.SimpleRequestTemplate;
import com.chbase.methods.jaxb.getthings3.request.GetThings3Request;
import com.chbase.methods.jaxb.getthings3.request.ThingFilterSpec;
import com.chbase.methods.jaxb.getthings3.request.ThingFormatSpec2;
import com.chbase.methods.jaxb.getthings3.request.ThingRequestGroup2;
import com.chbase.methods.jaxb.getthings3.request.ThingSectionSpec2;
import com.chbase.methods.jaxb.getthings3.response.GetThings3Response;
import com.chbase.thing.oxm.jaxb.basicV2.Basic;
import com.chbase.thing.oxm.jaxb.thing.Thing2;
import com.chbase.thing.oxm.jaxb.weight.Weight;

@RunWith(JMock.class)
public class GetThingsTest {

	private Mockery context = new JUnit4Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	/**
	 * Create the test case
	 *
	 */
	public GetThingsTest() {
	}

	@Test
	public void GetThing() throws Exception {
		SimpleRequestTemplate requestTemplate = TestHelpers.GetRequestTemplate();

		ThingRequestGroup2 group = new ThingRequestGroup2();

		ThingFilterSpec filter = new ThingFilterSpec();
		filter.getTypeId().add("3d34d87e-7fc1-4153-800f-f56592cb0d17");
		group.getFilter().add(filter);
		group.setMax(BigInteger.valueOf(30));

		ThingFormatSpec2 format = new ThingFormatSpec2();
		format.getSection().add(ThingSectionSpec2.CORE);
		format.getXml().add("");
		group.setFormat(format);

		GetThings3Request info = new GetThings3Request();
		info.getGroup().add(group);

		GetThings3Response response = (GetThings3Response) requestTemplate.makeRequest(info);

		Weight weight = (Weight) response.getGroup().get(0).getThing().get(0).getData();
	}

	@Test
	public void GetThingBasic() throws Exception {
		SimpleRequestTemplate requestTemplate = TestHelpers.GetRequestTemplate();

		ThingRequestGroup2 group = new ThingRequestGroup2();

		ThingFilterSpec filter = new ThingFilterSpec();
		filter.getTypeId().add("3b3e6b16-eb69-483c-8d7e-dfe116ae6092");
		group.getFilter().add(filter);

		ThingFormatSpec2 format = new ThingFormatSpec2();
		format.getSection().add(ThingSectionSpec2.CORE);
		format.getXml().add("");
		group.setFormat(format);
		group.setMax(BigInteger.valueOf(30));

		GetThings3Request info = new GetThings3Request();
		info.getGroup().add(group);

		GetThings3Response response = (GetThings3Response) requestTemplate.makeRequest(info);

		Basic weight = (Basic) response.getGroup().get(0).getThing().get(0).getData();
	}

	@Test
	public void GetThingWithAudit() throws Exception {
		SimpleRequestTemplate requestTemplate = TestHelpers.GetRequestTemplate();

		ThingRequestGroup2 group = new ThingRequestGroup2();

		ThingFilterSpec filter = new ThingFilterSpec();
		filter.getTypeId().add("3d34d87e-7fc1-4153-800f-f56592cb0d17");
		group.getFilter().add(filter);
		group.setMax(BigInteger.valueOf(30));

		ThingFormatSpec2 format = new ThingFormatSpec2();
		format.getSection().add(ThingSectionSpec2.CORE);
		format.getSection().add(ThingSectionSpec2.AUDITS);
		format.getXml().add("");
		group.setFormat(format);

		GetThings3Request info = new GetThings3Request();
		info.getGroup().add(group);

		GetThings3Response response = (GetThings3Response) requestTemplate.makeRequest(info);

		Thing2 thing = response.getGroup().get(0).getThing().get(0);
		Assert.assertNotNull(thing.getCreated());
	}

	@Test
	@Ignore
	public void GetThingToCCR() throws Exception {
		Request request = new Request();
		request.setFinalXsl("toccr");

		SimpleRequestTemplate requestTemplate = TestHelpers.GetRequestTemplate();
		ThingRequestGroup2 group = new ThingRequestGroup2();

		ThingFilterSpec filter = new ThingFilterSpec();
		filter.getTypeId().add("3d34d87e-7fc1-4153-800f-f56592cb0d17");
		group.getFilter().add(filter);
		group.setMax(BigInteger.valueOf(30));

		ThingFormatSpec2 format = new ThingFormatSpec2();
		format.getSection().add(ThingSectionSpec2.CORE);
		format.getSection().add(ThingSectionSpec2.AUDITS);
		format.getXml().add("");
		group.setFormat(format);

		GetThings3Request info = new GetThings3Request();
		info.getGroup().add(group);

		GetThings3Response response = (GetThings3Response) requestTemplate.makeRequest(request, info);
	}
}
