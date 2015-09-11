package com.microsoft.hsg.jaxb;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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
import com.microsoft.hsg.methods.jaxb.removethings.request.RemoveThingsRequest;
import com.microsoft.hsg.thing.oxm.jaxb.base.CodableValue;
import com.microsoft.hsg.thing.oxm.jaxb.thing.Thing2;

@RunWith(JMock.class)
public class TypeVersioningTest {
    
    private static String medication2Type = "30cafccc-047d-4288-94ef-643571f7919d";
    private static String medication1Type = "5c5f1223-f63c-4464-870c-3e36ba471def";
    
    private Mockery context = new JUnit4Mockery() {{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};

    @Test
    public void GetAllVersions() throws Exception
    {
        List<Thing2> t = GetAllMedications();
        String [] expected = new String[]{
            medication1Type,
            medication2Type
        };
        
        Assert.assertEquals(2, t.size());
        
        String[] actual = new String[2];
        actual[0] = t.get(0).getTypeId().getValue();
        actual[1] = t.get(1).getTypeId().getValue();
        
        AssertArrays(expected, actual);
    }
    
    @Test
    public void DownConvertVersions() throws Exception
    {
        SimpleRequestTemplate requestTemplate = new SimpleRequestTemplate(
        ConnectionFactory.getConnection());
        requestTemplate.setPersonId("75ac2c6c-c90e-4f7e-b74d-bb7e81787beb");
        requestTemplate.setRecordId("8c390004-3d41-4f5c-8f24-4841651579d6");
        
        ThingRequestGroup2 group = new ThingRequestGroup2();
        
        ThingFilterSpec filter = new ThingFilterSpec();
        filter.getTypeId().add(medication2Type);
        filter.getTypeId().add(medication1Type);
        group.getFilter().add(filter);
       
        ThingFormatSpec2 format = new ThingFormatSpec2();
        format.getSection().add(ThingSectionSpec2.CORE);
        format.getXml().add("");
        format.getTypeVersionFormat().add(medication1Type);
        group.setFormat(format);        
        
        GetThings3Request info = new GetThings3Request();
        info.getGroup().add(group);
        
        GetThings3Response response = 
            (GetThings3Response)requestTemplate.makeRequest(info);
        
        String [] expected = new String[]{
                medication1Type,
                medication1Type
            };
            
        Assert.assertEquals(2, response.getGroup().get(0).getThing().size());
        
        String[] actual = new String[2];
        actual[0] = response.getGroup().get(0).getThing().get(0).getTypeId().getValue();
        actual[1] = response.getGroup().get(0).getThing().get(1).getTypeId().getValue();
        
        AssertArrays(expected, actual);
    }
    
    @Test
    public void UpConvertVersions() throws Exception
    {
        SimpleRequestTemplate requestTemplate = new SimpleRequestTemplate(
        ConnectionFactory.getConnection());
        requestTemplate.setPersonId("75ac2c6c-c90e-4f7e-b74d-bb7e81787beb");
        requestTemplate.setRecordId("8c390004-3d41-4f5c-8f24-4841651579d6");
        
        ThingRequestGroup2 group = new ThingRequestGroup2();
        
        ThingFilterSpec filter = new ThingFilterSpec();
        filter.getTypeId().add(medication2Type);
        filter.getTypeId().add(medication1Type);
        group.getFilter().add(filter);
       
        ThingFormatSpec2 format = new ThingFormatSpec2();
        format.getSection().add(ThingSectionSpec2.CORE);
        format.getXml().add("");
        format.getTypeVersionFormat().add(medication2Type);
        group.setFormat(format);        
        
        GetThings3Request info = new GetThings3Request();
        info.getGroup().add(group);
        
        GetThings3Response response = 
            (GetThings3Response)requestTemplate.makeRequest(info);
        
        String [] expected = new String[]{
                medication2Type,
                medication2Type
            };
            
        Assert.assertEquals(2, response.getGroup().get(0).getThing().size());
        
        String[] actual = new String[2];
        actual[0] = response.getGroup().get(0).getThing().get(0).getTypeId().getValue();
        actual[1] = response.getGroup().get(0).getThing().get(1).getTypeId().getValue();
        
        AssertArrays(expected, actual);
    }

    @Before
    public void Cleanup() throws Exception
    {
        List<Thing2> things = GetAllMedications();
        RemoveThings(things);
        AddMeds();
    }
    
    private void AssertArrays(
            String[] expected, 
            String[] actual)
    {
        Arrays.sort(expected);
        Arrays.sort(actual);
        
        Assert.assertEquals(expected.length, actual.length);
        for(int i=0; i<expected.length; i++)
        {
            Assert.assertEquals(
                expected[i].toUpperCase(), 
                actual[i].toUpperCase());
        }
        
    }
    
    private void AddMeds() throws Exception
    {
        com.microsoft.hsg.thing.oxm.jaxb.medication1.Medication med1 = new com.microsoft.hsg.thing.oxm.jaxb.medication1.Medication();
        med1.setName("Test Med1");
        
        com.microsoft.hsg.thing.oxm.jaxb.medication.Medication med2 = new com.microsoft.hsg.thing.oxm.jaxb.medication.Medication();
        CodableValue name = new CodableValue();
        name.setText("Test Med2");
        med2.setName(name);
        
        List<Object> things = new ArrayList<Object>();
        things.add(med1);
        things.add(med2);
        PutThings(things);
    }
    
    private List<Thing2> GetAllMedications() throws Exception
    {
        SimpleRequestTemplate requestTemplate = new SimpleRequestTemplate(
        ConnectionFactory.getConnection());
        requestTemplate.setPersonId("75ac2c6c-c90e-4f7e-b74d-bb7e81787beb");
        requestTemplate.setRecordId("8c390004-3d41-4f5c-8f24-4841651579d6");
        
        ThingRequestGroup2 group = new ThingRequestGroup2();
        
        ThingFilterSpec filter = new ThingFilterSpec();
        filter.getTypeId().add(medication2Type);
        filter.getTypeId().add(medication1Type);
        group.getFilter().add(filter);
       
        ThingFormatSpec2 format = new ThingFormatSpec2();
        format.getSection().add(ThingSectionSpec2.CORE);
        format.getXml().add("");
        group.setFormat(format);        
        
        GetThings3Request info = new GetThings3Request();
        info.getGroup().add(group);
        
        GetThings3Response response = 
            (GetThings3Response)requestTemplate.makeRequest(info);
        
        return response.getGroup().get(0).getThing();
    }
    
    private void PutThings(List<Object> things) throws Exception
    {
        SimpleRequestTemplate requestTemplate = new SimpleRequestTemplate(
                ConnectionFactory.getConnection());
        requestTemplate.setPersonId("75ac2c6c-c90e-4f7e-b74d-bb7e81787beb");
        requestTemplate.setRecordId("8c390004-3d41-4f5c-8f24-4841651579d6");
            
        PutThings2Request request = new PutThings2Request();
        for( Object thing : things)
        {
            Thing2 t = new Thing2();
            t.setData(thing);
            request.getThing().add(t);
        }
        PutThings2Response response = (PutThings2Response)requestTemplate.makeRequest(request);
    }
    
    private void RemoveThings(List<Thing2> things) throws Exception
    {
        if (things.size() == 0) 
        {
            return;
        }
        
        RemoveThingsRequest removeThings = new RemoveThingsRequest();
        
        for ( Thing2 thing : things )
        {
            removeThings.getThingId().add(thing.getThingId());
        }
        
        SimpleRequestTemplate requestTemplate = new SimpleRequestTemplate(
        ConnectionFactory.getConnection());
        requestTemplate.setPersonId("75ac2c6c-c90e-4f7e-b74d-bb7e81787beb");
        requestTemplate.setRecordId("8c390004-3d41-4f5c-8f24-4841651579d6");
                
        requestTemplate.makeRequest(removeThings);
    }
}
