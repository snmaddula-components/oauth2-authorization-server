package snmaddula.components.authserver.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;

import snmaddula.components.authserver.service.UserInfoService;

/**
 * 
 * @author snmaddula
 *
 */
@Configuration
public class AppOauth2RequestFactory extends DefaultOAuth2RequestFactory {

	@Autowired
	private TokenStore jwtTokenStore;
	@Autowired
	private UserInfoService userInfoService;
	
	public AppOauth2RequestFactory(ClientDetailsService clientDetailsService) {
		super(clientDetailsService);
	}
	
	@Override
	public TokenRequest createTokenRequest(Map<String, String> requestParams,
			ClientDetails authenticatedClient) {
		if (requestParams.get("grant_type").equals("refresh_token")) {
			OAuth2RefreshToken refreshToken = jwtTokenStore.readRefreshToken(requestParams.get("refresh_token"));
			OAuth2Authentication authentication = jwtTokenStore.readAuthenticationForRefreshToken(refreshToken);
			SecurityContextHolder.getContext()
					.setAuthentication(new UsernamePasswordAuthenticationToken(authentication.getName(), null,
							userInfoService.loadUserByUsername(authentication.getName()).getAuthorities()));
		}
		return super.createTokenRequest(requestParams, authenticatedClient);
	}
}
