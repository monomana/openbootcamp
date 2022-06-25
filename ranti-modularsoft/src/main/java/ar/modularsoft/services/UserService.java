package ar.modularsoft.services;

import ar.modularsoft.api.dtos.UserDto;
import ar.modularsoft.api.dtos.UserResponseDto;
import ar.modularsoft.data.daos.UserRepository;
import ar.modularsoft.data.model.Role;
import ar.modularsoft.data.model.User;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

   /* public Optional< String > loginWhitMobile(String mobile) {
        return this.userRepository.findByMobile(mobile)
                .map(user -> jwtService.createToken(user.getMobile(), user.getFirstName(), user.getRole().name()));
    } */

    /* --------------- RETURN TOKEN STRING BASE64
    public Optional< String > loginWithUserName(String username) {
LogManager.getLogger(username);
System.out.println(username);
        return this.userRepository.findByUserName(username)
                .map(user ->
                    jwtService.createToken(user.getUserName(), user.getName(), Role.OPERATOR.toString() ));
                        // user.getRole().name()));
    }
--------- */

    public Optional< UserResponseDto > loginWithUserName(String username) {
        LogManager.getLogger(username);
        System.out.println(username);
        return this.userRepository.findByUsername(username)
                .map(user ->
                {
                    UserResponseDto userResponseDto= new UserResponseDto(user);
                    userResponseDto.setToken(jwtService.createToken(user.getUsername(),
                            user.getFirstName() + " " + user.getLastName(), Role.OPERATOR.toString()));
                    return userResponseDto;
                });
        // user.getRole().name()));
    }
   /* public void createUser(User user, Role roleClaim) {
        if (!authorizedRoles(roleClaim).contains(user.getRole())) {
            throw new ForbiddenException("Insufficient role to create this user: " + user);
        }
        this.assertNoExistByMobile(user.getMobile());
        user.setRegistrationDate(LocalDateTime.now());
        this.userRepository.save(user);
    }
*/

 /*   public Stream< User > readAll(Role roleClaim) {
        return this.userRepository.findByRoleIn(authorizedRoles(roleClaim)).stream();
    }

  */
    /*
    public Stream<User> readAllByTypeIva(String typeIva) {
        return this.userRepository.findByTypeIVA(typeIva).stream();
    }
*/


    private List< Role > authorizedRoles(Role roleClaim) {
        if (Role.ADMIN.equals(roleClaim)) {
            return List.of(Role.ADMIN, Role.MANAGER, Role.OPERATOR, Role.CUSTOMER);
        } else if (Role.MANAGER.equals(roleClaim)) {
            return List.of(Role.MANAGER, Role.OPERATOR, Role.CUSTOMER);
        } else if (Role.OPERATOR.equals(roleClaim)) {
            return List.of(Role.CUSTOMER);
        } else {
            return List.of();
        }
    }
/*
    private void assertNoExistByMobile(String mobile) {
        if (this.userRepository.findByMobile(mobile).isPresent()) {
            throw new ConflictException("The mobile already exists: " + mobile);
        }
    }

 */
/*
    public Stream< User > findByMobileAndFirstNameAndFamilyNameAndEmailAndDniContainingNullSafe(
            String mobile, String firstName, String familyName, String email, String dni, Role roleClaim) {
        return this.userRepository.findByMobileAndFirstNameAndFamilyNameAndEmailAndDniContainingNullSafe(
                mobile, firstName, familyName, email, dni, this.authorizedRoles(roleClaim)
        ).stream();
    }
 */
    /*
    public User readByMobileAssured(String mobile) {
        return this.userRepository.findByMobile(mobile)
                .orElseThrow(() -> new NotFoundException("The mobile don't exist: " + mobile));
    }

     */
}
