package snmaddula.components.authserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import snmaddula.components.authserver.entity.UserEntity;
import snmaddula.components.authserver.repo.UserRepo;

/**
 * 
 * @author snmaddula
 *
 */
@Service
public class UserInfoService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String input) {
		UserEntity user = null;

		if (input.contains("@"))
			user = userRepo.findByEmail(input);
		else
			user = userRepo.findByUsername(input);

		if (user == null)
			throw new BadCredentialsException("Bad credentials");

		new AccountStatusUserDetailsChecker().check(user);

		return user;
	}
}
