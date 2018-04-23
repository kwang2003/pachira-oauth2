package com.pachiraframework.oauth2.service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
/**
 * @author KevinWang
 *
 */
@Service
public class DefaultClientDetailsService implements ClientDetailsService {
	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		if(Objects.isNull(clientId) || !"client".equals(clientId)) {
			return null;
		}
		BaseClientDetails details = new BaseClientDetails();
		details.setClientId("client");
		details.setClientSecret("secret");
		Set<String> scopes = new HashSet<>();
		scopes.add("app");
		details.setScope(scopes);
		Set<String> authorizedGrantTypes = new HashSet<>();
		authorizedGrantTypes.add("password");
		authorizedGrantTypes.add("authorization_code");
		authorizedGrantTypes.add("client_credentials");
		details.setAuthorizedGrantTypes(authorizedGrantTypes);;
		return details;
	}

}
