package org.ivcode.stonks.repository.yahoo.websocket;

import java.util.Set;

class UnsubscribeMessage {
	private Set<String> unsubscribe;

	public Set<String> getUnsubscribe() {
		return unsubscribe;
	}

	public void setUnsubscribe(Set<String> unsubscribe) {
		this.unsubscribe = unsubscribe;
	}

}
