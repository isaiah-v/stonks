package org.ivcode.stonks.repository.yahoo.websocket;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class YahooWebSocketClientFactory {
	
	@Autowired
	private ObjectMapper mapper;
	
	@Value("${yahoo.webSocket}")
	private String url;
	
	public YahooWebSocketClient createYahooWebSocketClient(YahooTickerListener listener) {
		try {
			return new YahooWebSocketClient(new YahooWebSocket(new URI(url), mapper, listener));
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}
}
