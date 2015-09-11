package com.microsoft.hsg.jaxb.things;

import java.io.StringReader;
import java.math.BigInteger;
import java.util.Calendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.Before;
import org.junit.Test;

import com.microsoft.hsg.ConnectionFactory;
import com.microsoft.hsg.methods.jaxb.SimpleRequestTemplate;
import com.microsoft.hsg.methods.jaxb.getthings3.request.ThingFilterSpec;
import com.microsoft.hsg.methods.jaxb.getthings3.request.GetThings3Request;
import com.microsoft.hsg.methods.jaxb.getthings3.request.ThingFormatSpec2;
import com.microsoft.hsg.methods.jaxb.getthings3.request.ThingRequestGroup2;
import com.microsoft.hsg.methods.jaxb.getthings3.request.ThingSectionSpec2;
import com.microsoft.hsg.methods.jaxb.getthings3.response.GetThings3Response;
import com.microsoft.hsg.methods.jaxb.putthings2.request.PutThings2Request;
import com.microsoft.hsg.methods.jaxb.putthings2.response.PutThings2Response;
import com.microsoft.hsg.thing.oxm.jaxb.allergy.Allergy;
import com.microsoft.hsg.thing.oxm.jaxb.dates.DateTime;
import com.microsoft.hsg.thing.oxm.jaxb.heartrate.HeartRate;
import com.microsoft.hsg.thing.oxm.jaxb.thing.Thing2;
import com.microsoft.hsg.thing.oxm.jaxb.thing.TypeManager;

public class HeartRateTest {

	private SimpleRequestTemplate requestTemplate;
	
	@Before
	public void before()
	{
		requestTemplate = new SimpleRequestTemplate(
    			ConnectionFactory.getConnection());
    	requestTemplate.setPersonId("75ac2c6c-c90e-4f7e-b74d-bb7e81787beb");
    	requestTemplate.setRecordId("8c390004-3d41-4f5c-8f24-4841651579d6");
	}
	
	@Test
	public void testBassicPutGet() throws Exception
	{
		Thing2 thing = new Thing2();
    	thing.setData(createValidHeartRate());
		
		PutThings2Request request = new PutThings2Request();
    	request.getThing().add(thing);
    	
    	PutThings2Response response = (PutThings2Response)requestTemplate.makeRequest(request);
    	
    	ThingRequestGroup2 group = new ThingRequestGroup2();
    	
    	ThingFilterSpec filter = new ThingFilterSpec();
    	filter.getTypeId().add(TypeManager.getTypeForClass(Allergy.class));
    	group.getFilter().add(filter);
    	
    	ThingFormatSpec2 format = new ThingFormatSpec2();
    	format.getSection().add(ThingSectionSpec2.CORE);
    	format.getXml().add("");
    	group.setFormat(format);
    	group.setMax(BigInteger.valueOf(30));
    	
    	GetThings3Request info = new GetThings3Request();
    	info.getGroup().add(group);
    	
    	GetThings3Response thingsResponse = 
    		(GetThings3Response)requestTemplate.makeRequest(info);
    	
    	Allergy result = (Allergy)thingsResponse.getGroup().get(0).getThing().get(0).getData();
	}
	
	private HeartRate createValidHeartRate()
	{
		HeartRate hr = new HeartRate();
		hr.setValue(60);
		hr.setWhen(DateTime.fromCalendar(Calendar.getInstance()));
		
		return hr;
	}
	
}
