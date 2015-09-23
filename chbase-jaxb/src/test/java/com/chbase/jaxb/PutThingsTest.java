package com.chbase.jaxb;

import java.io.InputStream;
import java.util.Calendar;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.chbase.ApplicationConfig;
import com.chbase.Request;
import com.chbase.methods.jaxb.SimpleRequestTemplate;
import com.chbase.methods.jaxb.putthings2.request.PutThings2Request;
import com.chbase.methods.jaxb.putthings2.response.PutThings2Response;
import com.chbase.thing.oxm.jaxb.annotation.Annotation;
import com.chbase.thing.oxm.jaxb.base.DisplayValue;
import com.chbase.thing.oxm.jaxb.base.WeightValue;
import com.chbase.thing.oxm.jaxb.dates.DateTime;
import com.chbase.thing.oxm.jaxb.thing.Thing2;
import com.chbase.thing.oxm.jaxb.weight.Weight;

import junit.framework.Assert;

@RunWith(JMock.class)
public class PutThingsTest {

	private Mockery context = new JUnit4Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	/**
	 * Create the test case
	 *
	 */
	public PutThingsTest() {
	}

	private Thing2 GetThing() throws Exception {
		long weightValueInKg = 80;

		DisplayValue dv = new DisplayValue();
		dv.setUnits("lb");
		dv.setUnitsCode("lb");
		dv.setValue(weightValueInKg / 2.2);

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
	public void PutThing() throws Exception {
		SimpleRequestTemplate requestTemplate = TestHelpers.GetRequestTemplate();

		PutThings2Request request = new PutThings2Request();
		request.getThing().add(GetThing());

		PutThings2Response response = (PutThings2Response) requestTemplate.makeRequest(request);

		Assert.assertNotNull(response);
	}

	@Test
	public void PutThingWithBlob() throws Exception {
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
		pt_request.getThing().add(thing);

		PutThings2Response response = (PutThings2Response) requestTemplate.makeRequest(pt_request);

		Assert.assertNotNull(response);
	}

	@Test
	public void PutAnnotationThing() throws Exception {
		SimpleRequestTemplate requestTemplate = TestHelpers.GetRequestTemplate();

		Annotation note = new Annotation();
		note.setContent("fred");
		note.setWhen(DateTime.fromCalendar(Calendar.getInstance()));

		Thing2 thing = new Thing2();
		thing.setData(note);

		PutThings2Request request = new PutThings2Request();
		request.getThing().add(thing);

		PutThings2Response response = (PutThings2Response) requestTemplate.makeRequest(request);
	}
}
