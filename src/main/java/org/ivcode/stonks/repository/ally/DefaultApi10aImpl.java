package org.ivcode.stonks.repository.ally;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;

public class DefaultApi10aImpl extends DefaultApi10a {

	private final String requestTokenEndpoint;
	private final String accessTokenEndpoint;
	private final String authorizationUrl;
	
	public DefaultApi10aImpl(String requestTokenEndpoint, String accessTokenEndpoint, String authorizationUrl) {
		this.requestTokenEndpoint = requestTokenEndpoint;
		this.accessTokenEndpoint = accessTokenEndpoint;
		this.authorizationUrl = authorizationUrl;
	}

	@Override
	public String getRequestTokenEndpoint() {
		return requestTokenEndpoint;
	}

	@Override
	public String getAccessTokenEndpoint() {
		return accessTokenEndpoint;
	}

	@Override
	public String getAuthorizationUrl(Token requestToken) {
		return String.format(authorizationUrl, requestToken.getToken());
	}

}
