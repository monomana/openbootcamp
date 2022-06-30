package ar.modularsoft.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {
	private final Auth auth = new Auth();
	private final OAuth2 oauth2 = new OAuth2();

	public static class Auth {
		private static final String BEARER = "Bearer ";
		private static final String USER_CLAIM = "user";
		private static final String NAME_CLAIM = "name";
		private static final String ROLE_CLAIM = "role";


		private  String tokenIssuer;

		private String tokenSecret;
		private long tokenExpirationMsec;
		private long refreshTokenExpirationMs;

		public String getTokenIssuer() {
			return tokenIssuer;
		}
		public void setTokenIssuer(String tokenIssuer) {
			this.tokenIssuer = tokenIssuer;
		}

		public String getTokenSecret() {
			return tokenSecret;
		}

		public void setTokenSecret(String tokenSecret) {
			this.tokenSecret = tokenSecret;
		}

		public long getTokenExpirationMsec() {
			return tokenExpirationMsec;
		}

		public void setTokenExpirationMsec(long tokenExpirationMsec) {
			this.tokenExpirationMsec = tokenExpirationMsec;
		}

		public long getRefreshTokenExpirationMs() {
			return refreshTokenExpirationMs;
		}

		public void setRefreshTokenExpirationMs(long refreshTokenExpirationMs) {
			this.refreshTokenExpirationMs = refreshTokenExpirationMs;
		}


	}

	public static final class OAuth2 {
		private List<String> authorizedRedirectUris = new ArrayList<>();

		public List<String> getAuthorizedRedirectUris() {
			return authorizedRedirectUris;
		}

		public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
			this.authorizedRedirectUris = authorizedRedirectUris;
			return this;
		}
	}

	public Auth getAuth() {
		return auth;
	}

	public OAuth2 getOauth2() {
		return oauth2;
	}
}