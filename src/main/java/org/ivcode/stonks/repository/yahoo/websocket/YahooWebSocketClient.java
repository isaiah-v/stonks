package org.ivcode.stonks.repository.yahoo.websocket;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

/**
 * A user 
 * @author isaiah
 *
 */
public class YahooWebSocketClient {
	
	private final YahooWebSocket websocket;
		
	YahooWebSocketClient(YahooWebSocket websocket) {
		this.websocket = websocket;
	}

	public Set<String> getSubscriptionSymbols() {
		return websocket.getSubscriptionSymbols();
	}
	
	public void setSubscriptionSymbols(Collection<String> symbols) throws IOException {
		websocket.setSubscriptionSymbols(symbols);
	}

	public boolean isClosed() {
		return websocket.isDisconncected();
	}
	
	public void close() throws IOException {
		websocket.close();
	}
	
	public void open() throws IOException {
		websocket.open();
	}
}
