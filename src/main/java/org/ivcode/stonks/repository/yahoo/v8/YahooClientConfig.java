package org.ivcode.stonks.repository.yahoo.v8;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class YahooClientConfig {
	
	@Bean("yahoo.okHttpClient")
	public OkHttpClient createOkHttpClient() {
		OkHttpClient client = new OkHttpClient.Builder()
			    .build();
		
		return client;
	}
	
	@Bean
	public YahooClassicClient createDoubleDownClient(
			@Qualifier("yahoo.okHttpClient") OkHttpClient client,
			@Value("${yahoo.classicUrl}") String url,
			ObjectMapper mapper) {
		
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(url)
				.client(client)
				.addConverterFactory(JacksonConverterFactory.create(mapper))
				.build();
		
		return retrofit.create(YahooClassicClient.class);
	}
	
	@Bean
	public YahooQueryClient createYahooClient(
			@Qualifier("yahoo.okHttpClient") OkHttpClient client,
			@Value("${yahoo.query1Url}") String url,
			ObjectMapper mapper) {
		
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(url)
				.client(client)
				.addConverterFactory(JacksonConverterFactory.create(mapper))
				.build();
		
		return retrofit.create(YahooQueryClient.class);
	}
}
