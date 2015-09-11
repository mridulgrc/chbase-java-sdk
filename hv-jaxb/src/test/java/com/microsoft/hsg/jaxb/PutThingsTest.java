package com.microsoft.hsg.jaxb;

import java.io.InputStream;
import java.util.Calendar;

import junit.framework.Assert;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.microsoft.hsg.ApplicationConfig;
import com.microsoft.hsg.Connection;
import com.microsoft.hsg.ConnectionFactory;
import com.microsoft.hsg.Request;
import com.microsoft.hsg.methods.jaxb.SimpleRequestTemplate;
import com.microsoft.hsg.methods.jaxb.putthings2.request.PutThings2Request;
import com.microsoft.hsg.methods.jaxb.putthings2.response.PutThings2Response;
import com.microsoft.hsg.thing.oxm.jaxb.annotation.Annotation;
import com.microsoft.hsg.thing.oxm.jaxb.base.DisplayValue;
import com.microsoft.hsg.thing.oxm.jaxb.base.WeightValue;
import com.microsoft.hsg.thing.oxm.jaxb.dates.DateTime;
import com.microsoft.hsg.thing.oxm.jaxb.thing.Thing2;
import com.microsoft.hsg.thing.oxm.jaxb.weight.Weight;

@RunWith(JMock.class)
public class PutThingsTest {


	private Mockery context = new JUnit4Mockery() {{
		setImposteriser(ClassImposteriser.INSTANCE);
	}};

	/**
	 * Create the test case
	 *
	 */
	public PutThingsTest()
	{
	}

	private Thing2 GetThing() throws Exception
	{
		long weightValueInKg = 80;

		DisplayValue dv = new DisplayValue();
		dv.setUnits("lb");
		dv.setUnitsCode("lb");
		dv.setValue(weightValueInKg/2.2);

		WeightValue wv = new WeightValue();
		wv.setKg(weightValueInKg);
		wv.setDisplay(dv);

		Weight weight = new Weight();
		weight.setValue(wv);
		weight.setWhen(DateTime.fromCalendar(Calendar.getInstance()));

		Thing2 thing = new Thing2();
		thing.setData(weight);
		return thing;
	}

	@Test
	public void PutThing() throws Exception
	{
		SimpleRequestTemplate requestTemplate = TestHelpers.GetRequestTemplate();

		PutThings2Request request = new PutThings2Request();
		request.getThing().add(GetThing());

		PutThings2Response response = (PutThings2Response)requestTemplate.makeRequest(request);

		Assert.assertNotNull(response);
	}
	
	@Test
	public void PutThingWithBlob() throws Exception
	{
		SimpleRequestTemplate requestTemplate = TestHelpers.GetRequestTemplate();
		Thing2 thing = GetThing();
		
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream("largeimage.jpg");
		
		Request request = new Request();
		request.setOfflineUserId(ApplicationConfig.Test_PersonID);
		request.setRecordId(ApplicationConfig.Test_RecordID);
		
		thing.addBlob("", is, "image/jpg", request);
		
		is.close();

		PutThings2Request pt_request = new PutThings2Request();
		pt_request .getThing().add(thing);

		PutThings2Response response = (PutThings2Response)requestTemplate.makeRequest(pt_request);

		Assert.assertNotNull(response);
	}



	@Test
	public void PutAnnotationThing() throws Exception
	{
		SimpleRequestTemplate requestTemplate = TestHelpers.GetRequestTemplate();

		Annotation note = new Annotation();
		note.setContent("fred");
		note.setWhen(DateTime.fromCalendar(Calendar.getInstance()));

		Thing2 thing = new Thing2();
		thing.setData(note);

		PutThings2Request request = new PutThings2Request();
		request.getThing().add(thing);

		PutThings2Response response = (PutThings2Response)requestTemplate.makeRequest(request);
	}
}
