package org.ivcode.stonks.repository.yahoo.websocket;

import java.util.Set;

class SubscribeMessage {
	private Set<String> subscribe;

	public Set<String> getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(Set<String> subscribe) {
		this.subscribe = subscribe;
	}

}
