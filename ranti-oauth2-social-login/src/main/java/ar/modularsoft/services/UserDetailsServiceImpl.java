package ar.modularsoft.services;

import ar.modularsoft.data.daos.UserRepositoryRanti;
import ar.modularsoft.data.model.Role;
import ar.modularsoft.data.model.User;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
// @Qualifier("mds.users")
public class UserDetailsServiceImpl // implements UserDetailsService
{

    private final UserRepositoryRanti userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepositoryRanti userRepository) {
        this.userRepository = userRepository;
    }

  //  @Override
    public UserDetails loadUserByUsername(final String mobile) {
        User user = userRepository.findByUsername(mobile)
                .orElseThrow(() -> new UsernameNotFoundException("user not found. " + mobile));
        LogManager.getLogger("Name "+user.getUsername());
        System.err.println("Name "+user.getUsername());
        return this.userBuilder(user.getUsername(), user.getPassword(), new Role[]{Role.AUTHENTICATED}, user.getState() == 1);
    }

    private org.springframework.security.core.userdetails.User userBuilder(String mobile, String password, Role[] roles,
                                                                           boolean active) {
        List< GrantedAuthority > authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.withPrefix()));
        }
        LogManager.getLogger("Name "+password);
        System.err.println("Name "+mobile);
        return new org.springframework.security.core.userdetails.User(mobile, password, active, true,
                true, true, authorities);
    }
}
