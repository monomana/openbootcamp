package ar.modularsoft.data.daos;

import ar.modularsoft.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository< User, Integer > {
  //  Optional< User > findByMobile(String mobile);
    Optional< User > findByUsername(String username);
 //   List< User > findByRoleIn(Collection< Role > roles); typeIVA
 //List< User > findByTypeIVA( String typeIVA);

 @Query(
         value = "SELECT c.CLI_PASS FROM client c WHERE c.CLI_USER = ?1",
         nativeQuery = true)
 Optional< User > findByName(String username);

 /*   @Query("select u from User u where " +
            "(?1 is null or u.mobile like concat('%',?1,'%')) and " +
            "(?2 is null or lower(u.firstName) like lower(concat('%',?2,'%'))) and" +
            "(?3 is null or lower(u.familyName) like lower(concat('%',?3,'%'))) and" +
            "(?4 is null or lower(u.email) like lower(concat('%',?4,'%'))) and" +
            "(?5 is null or lower(u.dni) like lower(concat('%',?5,'%'))) and" +
            "(u.role in ?6)")
    List< User > findByMobileAndFirstNameAndFamilyNameAndEmailAndDniContainingNullSafe(
            String mobile, String firstName, String familyName, String email, String dni, Collection< Role > roles);

  */
}
