package com.pachiraframework.oauth2.config;

import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.pachiraframework.oauth2.service.DefaultClientDetailsService;
import com.pachiraframework.oauth2.service.DefaultUserDetailsService;

/**
 * @author KevinWang
 *
 */
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private DefaultUserDetailsService userDetailsService;
	@Autowired
	private DefaultClientDetailsService clientDetailsService;

	@Override
	public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
		oauthServer.allowFormAuthenticationForClients();
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(clientDetailsService);
	}

	@Override
	public void configure(final AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// @formatter:off
		endpoints.authenticationManager(authenticationManager).userDetailsService(userDetailsService)
				.accessTokenConverter(accessTokenConverter());
		// @formatter:on
	}
    
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter() {
			@Override
			public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
				String grantType = authentication.getOAuth2Request().getGrantType();
				//只有如下两种模式才能获取到当前用户信息
				if("authorization_code".equals(grantType) || "password".equals(grantType)) {
					String userName = authentication.getUserAuthentication().getName();
					final Map<String, Object> additionalInformation = new HashMap<>();
					additionalInformation.put("user_name", userName);
					((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
				}
				OAuth2AccessToken token = super.enhance(accessToken, authentication);
				return token;
			}
		};
		KeyPair keyPair = new KeyStoreKeyFactory(new ClassPathResource("kevin_key.jks"), "123456".toCharArray())
				.getKeyPair("kevin_key");
		converter.setKeyPair(keyPair);
		return converter;
	}
}
