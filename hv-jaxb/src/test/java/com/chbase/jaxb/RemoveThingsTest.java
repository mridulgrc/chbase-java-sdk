package com.chbase.jaxb;

import java.util.Calendar;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.chbase.ConnectionFactory;
import com.chbase.methods.jaxb.SimpleRequestTemplate;
import com.chbase.methods.jaxb.putthings2.request.PutThings2Request;
import com.chbase.methods.jaxb.putthings2.response.PutThings2Response;
import com.chbase.methods.jaxb.removethings.request.RemoveThingsRequest;
import com.chbase.thing.oxm.jaxb.base.DisplayValue;
import com.chbase.thing.oxm.jaxb.base.WeightValue;
import com.chbase.thing.oxm.jaxb.dates.Date;
import com.chbase.thing.oxm.jaxb.dates.DateTime;
import com.chbase.thing.oxm.jaxb.thing.Thing2;
import com.chbase.thing.oxm.jaxb.thing.ThingKey;
import com.chbase.thing.oxm.jaxb.thing.ThingType;
import com.chbase.thing.oxm.jaxb.weight.Weight;

@RunWith(JMock.class)
public class RemoveThingsTest {
	

	private Mockery context = new JUnit4Mockery() {{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};

    private SimpleRequestTemplate requestTemplate;
    
    @Before
    public void setUp()
    {
    	this.requestTemplate = TestHelpers.GetRequestTemplate();
    }
    
	/**
     * Create the test case
     *
     */
    @Test
    public void RemoveThingsTest() throws Exception
    {
    	ThingKey thingToRemove = PutNewThing();
    	RemoveThingsRequest requestInfo = new RemoveThingsRequest();
    	requestInfo.getThingId().add(thingToRemove);
    	
    	requestTemplate.makeRequest(requestInfo);
    }
    
    
  
    public ThingKey PutNewThing() throws Exception
    {
    	PutThings2Request request = new PutThings2Request();
    	Weight weight = new Weight();
    	
    	WeightValue wv = new WeightValue();
    	wv.setKg(80);
    	weight.setValue(wv);
    	
    	Calendar now = Calendar.getInstance();
    	Date date = new Date();
    	date.setD(now.get(Calendar.DAY_OF_MONTH));
    	date.setM(now.get(Calendar.MONTH) + 1);
    	date.setY(now.get(Calendar.YEAR));
    	
    	DateTime when = new DateTime();
    	when.setDate(date);
    	
    	weight.setWhen(when);
    	
    	DisplayValue dv = new DisplayValue();
    	dv.setUnits("lb");
    	dv.setUnitsCode("lb");
    	wv.setDisplay(dv);
    	
    	Thing2 thing = new Thing2();
    	thing.setData(weight);
    	
    	ThingType thingType = new ThingType();
    	thingType.setValue("3d34d87e-7fc1-4153-800f-f56592cb0d17");
    	thing.setTypeId(thingType);
    	
    	request.getThing().add(thing);
    	
    	PutThings2Response response = (PutThings2Response)requestTemplate.makeRequest(request);
    	return response.getThingId().get(0);
    }
}
