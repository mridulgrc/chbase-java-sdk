package com.chbase.jaxb.things;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;

import com.chbase.ConnectionFactory;
import com.chbase.jaxb.TestHelpers;
import com.chbase.methods.jaxb.SimpleRequestTemplate;
import com.chbase.methods.jaxb.getthings3.request.GetThings3Request;
import com.chbase.methods.jaxb.getthings3.request.ThingFilterSpec;
import com.chbase.methods.jaxb.getthings3.request.ThingFormatSpec2;
import com.chbase.methods.jaxb.getthings3.request.ThingRequestGroup2;
import com.chbase.methods.jaxb.getthings3.request.ThingSectionSpec2;
import com.chbase.methods.jaxb.getthings3.response.GetThings3Response;
import com.chbase.methods.jaxb.putthings2.request.PutThings2Request;
import com.chbase.methods.jaxb.putthings2.response.PutThings2Response;
import com.chbase.thing.oxm.jaxb.base.Name;
import com.chbase.thing.oxm.jaxb.base.Person;
import com.chbase.thing.oxm.jaxb.thing.Thing2;
import com.chbase.thing.oxm.jaxb.thing.TypeManager;

public class ContactTest {

	private SimpleRequestTemplate requestTemplate;

	@Before
	public void before() {
		requestTemplate = TestHelpers.GetRequestTemplate();
	}

	@Test
	public void testBasicPutGet() throws Exception {
		Thing2 thing = new Thing2();
		thing.setData(createValidContact());

		PutThings2Request request = new PutThings2Request();
		request.getThing().add(thing);

		PutThings2Response response = (PutThings2Response) requestTemplate.makeRequest(request);

		ThingRequestGroup2 group = new ThingRequestGroup2();

		ThingFilterSpec filter = new ThingFilterSpec();
		filter.getTypeId().add(TypeManager.getTypeForClass(Person.class));
		group.getFilter().add(filter);

		ThingFormatSpec2 format = new ThingFormatSpec2();
		format.getSection().add(ThingSectionSpec2.CORE);
		format.getXml().add("");
		group.setFormat(format);
		group.setMax(BigInteger.valueOf(30));

		GetThings3Request info = new GetThings3Request();
		info.getGroup().add(group);

		GetThings3Response thingsResponse = (GetThings3Response) requestTemplate.makeRequest(info);

		Person result = (Person) thingsResponse.getGroup().get(0).getThing().get(0).getData();
	}

	private Person createValidContact() {
		Name name = new Name();
		name.setFull("Dr. Who");
		Person person = new Person();
		person.setName(name);

		return person;
	}
}
