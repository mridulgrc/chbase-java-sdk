package com.chbase.jaxb;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.chbase.ConnectionFactory;
import com.chbase.methods.jaxb.SimpleRequestTemplate;
import com.chbase.methods.jaxb.geteventsubscriptions.request.GetEventSubscriptionsRequest;
import com.chbase.methods.jaxb.geteventsubscriptions.response.GetEventSubscriptionsResponse;
import com.chbase.methods.jaxb.subscribetoevent.request.SubscribeToEventRequest;
import com.chbase.methods.jaxb.subscribetoevent.response.SubscribeToEventResponse;
import com.chbase.methods.jaxb.subscription.Common;
import com.chbase.methods.jaxb.subscription.HVEventingSharedKey;
import com.chbase.methods.jaxb.subscription.HttpNotificationChannel;
import com.chbase.methods.jaxb.subscription.NotificationAuthenticationInfo;
import com.chbase.methods.jaxb.subscription.NotificationChannel;
import com.chbase.methods.jaxb.subscription.RecordItemChangedEvent;
import com.chbase.methods.jaxb.subscription.RecordItemChangedEventFilter;
import com.chbase.methods.jaxb.subscription.RecordItemChangedEventFilters;
import com.chbase.methods.jaxb.subscription.Subscription;
import com.chbase.methods.jaxb.subscription.TypeIds;
import com.chbase.methods.jaxb.unsubscribetoevent.request.UnsubscribeToEventRequest;

@RunWith(JMock.class)
public class SubscriptionsTest {

	private Mockery context = new JUnit4Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	@Before
	public void setUp() throws Exception {
		removeSubscriptions();
	}

	@After
	public void tearDown() throws Exception {
		removeSubscriptions();
	}

	private void removeSubscriptions() throws Exception {
		GetEventSubscriptionsRequest request = new GetEventSubscriptionsRequest();

		SimpleRequestTemplate requestTemplate = new SimpleRequestTemplate(ConnectionFactory.getConnection());

		GetEventSubscriptionsResponse response = (GetEventSubscriptionsResponse) requestTemplate.makeRequest(request);

		for (Subscription subscription : response.getSubscriptions().getSubscription()) {
			UnsubscribeToEventRequest unsubscribe = new UnsubscribeToEventRequest();
			unsubscribe.setSubscriptionId(subscription.getCommon().getId());

			requestTemplate.makeRequest(unsubscribe);
		}
	}

	@Test
	public void createSubscriptionTest() throws Exception {
		HVEventingSharedKey sharedKey = new HVEventingSharedKey();
		sharedKey.setNotificationKey(
				"xaBOODnoKbyKetat+D2WM1px5YzZ1MN+TvKJWtYq7iPYVMmnUGcMjKn3hmTlHXwIQkDQ/k2J4dpJYOyuGnQoQQ==");
		sharedKey.setNotificationKeyVersionId("1");

		NotificationAuthenticationInfo nai = new NotificationAuthenticationInfo();
		nai.setHvEventingSharedKey(sharedKey);

		HttpNotificationChannel http = new HttpNotificationChannel();
		http.setUrl("http://nobody.contoso.com/");

		NotificationChannel channel = new NotificationChannel();
		channel.setHttpNotificationChannel(http);

		Common common = new Common();
		common.setNotificationAuthenticationInfo(nai);
		common.setNotificationChannel(channel);

		TypeIds typeIds = new TypeIds();
		typeIds.getTypeId().add("879e7c04-4e8a-4707-9ad3-b054df467ce4");

		RecordItemChangedEventFilter filter = new RecordItemChangedEventFilter();
		filter.setTypeIds(typeIds);

		RecordItemChangedEventFilters filters = new RecordItemChangedEventFilters();
		filters.getFilter().add(filter);

		RecordItemChangedEvent changedEvent = new RecordItemChangedEvent();
		changedEvent.setFilters(filters);

		Subscription subscription = new Subscription();
		subscription.setRecordItemChangedEvent(changedEvent);
		subscription.setCommon(common);

		SubscribeToEventRequest subscriptionRequest = new SubscribeToEventRequest();
		subscriptionRequest.setSubscription(subscription);

		SimpleRequestTemplate requestTemplate = new SimpleRequestTemplate(ConnectionFactory.getConnection());

		SubscribeToEventResponse response = (SubscribeToEventResponse) requestTemplate.makeRequest(subscriptionRequest);
	}
}
