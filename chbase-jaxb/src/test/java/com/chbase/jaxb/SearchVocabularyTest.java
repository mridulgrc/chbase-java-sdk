package com.chbase.jaxb;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.chbase.ConnectionFactory;
import com.chbase.methods.jaxb.SimpleRequestTemplate;
import com.chbase.methods.jaxb.searchvocabulary.request.SearchVocabularyRequest;
import com.chbase.methods.jaxb.searchvocabulary.request.VocabularySearchParams;
import com.chbase.methods.jaxb.searchvocabulary.request.VocabularySearchString;
import com.chbase.methods.jaxb.searchvocabulary.response.SearchVocabularyResponse;
import com.chbase.methods.jaxb.vocab.VocabularyKey;

import junit.framework.Assert;

@RunWith(JMock.class)
public class SearchVocabularyTest {

	private Mockery context = new JUnit4Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	/**
	 * Create the test case
	 *
	 */
	public SearchVocabularyTest() {
	}

	@Test
	@Ignore
	public void SearchVocabulary() throws Exception {
		SimpleRequestTemplate requestTemplate = new SimpleRequestTemplate(ConnectionFactory.getConnection());

		VocabularyKey key = new VocabularyKey();
		key.setName("time-zones");

		VocabularySearchString searchString = new VocabularySearchString();
		searchString.setValue("Yellow");
		searchString.setSearchMode("Contains");

		VocabularySearchParams params = new VocabularySearchParams();
		params.setSearchString(searchString);

		SearchVocabularyRequest request = new SearchVocabularyRequest();
		request.setTextSearchParameters(params);
		request.setVocabularyKey(key);

		SearchVocabularyResponse response = (SearchVocabularyResponse) requestTemplate.makeRequest(request);

		Assert.assertNotNull(response);
	}
}
