package org.ivcode.stonks.market.live;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.Extension;

public class WebSocketExtention implements Extension {

	private final String name;
	private final List<Parameter> parameters = new ArrayList<>();

	public WebSocketExtention(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<Parameter> getParameters() {
		return parameters;
	}

	public void addParameter(String name, String value) {
		parameters.add(new WebSocketParameter(name, value));
	}

	private static class WebSocketParameter implements Extension.Parameter {

		private final String name;
		private final String value;

		public WebSocketParameter(String name, String value) {
			this.name = name;
			this.value = value;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String getValue() {
			return value;
		}
	}
}
