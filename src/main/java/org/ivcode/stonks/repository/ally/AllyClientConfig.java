package org.ivcode.stonks.repository.ally;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.ivcode.stonks.repository.ally.model.AccountListResponse;
import org.ivcode.stonks.repository.ally.model.AccountResponse;
import org.ivcode.stonks.repository.ally.model.Clock;
import org.ivcode.stonks.repository.ally.model.HistoryResponse;
import org.ivcode.stonks.repository.ally.model.NewsArticleResponse;
import org.ivcode.stonks.repository.ally.model.NewsSearchResponse;
import org.ivcode.stonks.repository.ally.model.OrderResponse;
import org.ivcode.stonks.repository.ally.model.Profile;
import org.ivcode.stonks.repository.ally.model.QuoteResponse;
import org.ivcode.stonks.repository.ally.model.TimeSales;
import org.ivcode.stonks.repository.ally.model.TopList;
import org.ivcode.stonks.repository.ally.model.fxml.FIXML;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Api;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@Configuration
public class AllyClientConfig {
	
	@Bean("ally.oath")
	public OAuthService createOAuthService(
			@Qualifier("ally.api") Api api,
			@Value("${ally.consumerKey}") String consumerKey,
			@Value("${ally.consumeSecret}") String consumeSecret) {
		
		return new ServiceBuilder()
                .provider(TradeKingApi.class)
                .apiKey(consumerKey)
                .apiSecret(consumeSecret)
                .build();
	}
	
	@Bean("ally.token")
	public Token createToken(
			@Value("${ally.oauthToken}") String oauthToken,
			@Value("${ally.oauthTokenSecret}") String oauthTokenSecret) {
		
		return new Token(oauthToken, oauthTokenSecret);
	}
	
	@Bean("ally.api")
	public Api createApi(
			@Value("${ally.authorizeUrl}") String authorizeUrl,
			@Value("${ally.requestTokenResource}") String requestTokenResource,
			@Value("${ally.accessTokenResource}") String accessTokenResource) {
		
		return new DefaultApi10aImpl(requestTokenResource, accessTokenResource, authorizeUrl);
	}
	
	@Bean("ally.jaxbContext")
	public JAXBContext createJAXBContext() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(
				TimeSales.class,
				Profile.class,
				TopList.class,
				Clock.class,
				QuoteResponse.class,
				NewsSearchResponse.class,
				NewsArticleResponse.class,
				AccountListResponse.class,
				AccountResponse.class,
				OrderResponse.class,
				HistoryResponse.class,
				FIXML.class);
		
		return context;
	}
	
	@Bean("ally.unmarshaller")
	@Scope(scopeName="prototype", proxyMode = ScopedProxyMode.INTERFACES)
	public Unmarshaller createUnmarshaller(@Qualifier("ally.jaxbContext") JAXBContext context) throws JAXBException {
		Unmarshaller unmarshaller = context.createUnmarshaller();
		return unmarshaller;
	}
	
	@Bean("ally.marshaller")
	@Scope(scopeName="prototype", proxyMode = ScopedProxyMode.INTERFACES)
	public Marshaller createMarshaller(@Qualifier("ally.jaxbContext") JAXBContext context) throws JAXBException {
		Marshaller marshaller = context.createMarshaller();
		return marshaller;
	}
}
