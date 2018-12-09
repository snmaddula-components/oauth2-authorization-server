package snmaddula.components.authserver.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import lombok.AllArgsConstructor;

/**
 * 
 * @author snmaddula
 *
 */
@Configuration
@AllArgsConstructor
@EnableAuthorizationServer
public class AppOAuth2AuthServerConfig extends AuthorizationServerConfigurerAdapter  {

	private DataSource dataSource;
	private PasswordEncoder passwordEncoder;
	private AppJwtTokenEnhancer jwtTokenEnhancer;
	private UserDetailsService userDetailsService;
	private ClientDetailsService clientDetailsService;
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients
			.jdbc(dataSource)
			.passwordEncoder(passwordEncoder);
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer
			.tokenKeyAccess("permitAll()")
			.checkTokenAccess("isAuthenticated()");
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
			.requestFactory(requestFactory())
			.tokenEnhancer(jwtTokenEnhancer)
			.userDetailsService(userDetailsService)
			.authenticationManager(authenticationManager)
			.tokenStore(jwtTokenStore());
	}

	@Bean
	public OAuth2RequestFactory requestFactory() {
		AppOauth2RequestFactory requestFactory = new AppOauth2RequestFactory(clientDetailsService);
		requestFactory.setCheckUserScopes(true);
		return requestFactory;
	}
	
	@Bean
	public TokenStore jwtTokenStore() {
		return new JwtTokenStore(jwtTokenEnhancer);
	}

}
