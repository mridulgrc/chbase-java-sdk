package com.microsoft.hsg.jaxb;

import java.util.Calendar;

import junit.framework.Assert;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.microsoft.hsg.ConnectionFactory;
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
    
    @Test
    public void PutThing() throws Exception
    {
    	SimpleRequestTemplate requestTemplate = new SimpleRequestTemplate(
    			ConnectionFactory.getConnection());
    	requestTemplate.setPersonId("75ac2c6c-c90e-4f7e-b74d-bb7e81787beb");
    	requestTemplate.setRecordId("8c390004-3d41-4f5c-8f24-4841651579d6");
    		
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
    	
    	PutThings2Request request = new PutThings2Request();
    	request.getThing().add(thing);
    	
    	PutThings2Response response = (PutThings2Response)requestTemplate.makeRequest(request);
    	
    	Assert.assertNotNull(response);
    }
    
    @Test
    public void PutAnnotationThing() throws Exception
    {
    	SimpleRequestTemplate requestTemplate = new SimpleRequestTemplate(
    			ConnectionFactory.getConnection());
    	requestTemplate.setPersonId("75ac2c6c-c90e-4f7e-b74d-bb7e81787beb");
    	requestTemplate.setRecordId("8c390004-3d41-4f5c-8f24-4841651579d6");
        
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
