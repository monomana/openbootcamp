package ar.modularsoft.service;

import ar.modularsoft.dto.LocalUser;
import ar.modularsoft.exception.ResourceNotFoundException;
import ar.modularsoft.model.User;
import ar.modularsoft.util.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 
 * @author Modularsoft
 *
 */
@Primary
@Service("localUserDetailService")
public class LocalUserDetailService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	@Transactional
	public LocalUser loadUserByUsername(final String email) throws UsernameNotFoundException {
		User user = userService.findUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
		if (user == null) {
			throw new UsernameNotFoundException("User " + email + " was not found in the database");
		}
		return createLocalUser(user);
	}

	@Transactional
	public LocalUser loadUserById(Long id) {
		User user = userService.findUserById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		return createLocalUser(user);
	}

	/**
	 * @param user
	 * @return
	 */
	private LocalUser createLocalUser(User user) {
		return new LocalUser(user.getEmail(), user.getPassword(), user.isState(), true, true, true, GeneralUtils.buildSimpleGrantedAuthorities(user.getRoles()), user);
	}
}
