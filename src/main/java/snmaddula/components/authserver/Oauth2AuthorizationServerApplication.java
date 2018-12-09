package snmaddula.components.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * OAuth2 Authorization Server using JWT token Application
 * 
 * @author snmaddula
 *
 */
@SpringBootApplication
public class Oauth2AuthorizationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Oauth2AuthorizationServerApplication.class, args);
	}
}
