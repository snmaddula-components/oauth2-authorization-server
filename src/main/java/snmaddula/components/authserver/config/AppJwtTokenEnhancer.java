package snmaddula.components.authserver.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.stereotype.Component;

import snmaddula.components.authserver.entity.UserEntity;

/**
 * 
 * @author snmaddula
 *
 */
@Component
public class AppJwtTokenEnhancer extends JwtAccessTokenConverter {
	
	public AppJwtTokenEnhancer() {
		setKeyPair(
				new KeyStoreKeyFactory(
						new ClassPathResource("jwt.jks"), 
						"password".toCharArray())
				.getKeyPair("jwt"));
	}

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		UserEntity user = (UserEntity) authentication.getPrincipal();
		Map<String, Object> info = new LinkedHashMap<String, Object>(accessToken.getAdditionalInformation());
		info.put("email", user.getEmail());
		DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
		customAccessToken.setAdditionalInformation(info);
		return super.enhance(customAccessToken, authentication);
		
	}
}
