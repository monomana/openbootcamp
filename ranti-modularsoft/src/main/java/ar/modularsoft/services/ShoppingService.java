package ar.modularsoft.services;

import ar.modularsoft.data.daos.ShoppingRepository;
import ar.modularsoft.data.model.Shopping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Stream;

@Service
public class ShoppingService {

    private final ShoppingRepository shoppingRepository;

    @Autowired
    public ShoppingService(ShoppingRepository shoppingRepository) {
        this.shoppingRepository = shoppingRepository;
    }

    public Stream<Shopping> readAll() {
        return this.shoppingRepository.findAll().stream();
    }

   /* public Optional< String > loginWhitMobile(String mobile) {
        return this.categoryRepository.findByMobile(mobile)
                .map(category -> jwtService.createToken(category.getMobile(), category.getFirstName(), category.getRole().name()));
    } */

    /* --------------- RETURN TOKEN STRING BASE64
    public Optional< String > loginWithCategoryName(String categoryname) {
LogManager.getLogger(categoryname);
System.out.println(categoryname);
        return this.categoryRepository.findByCategoryName(categoryname)
                .map(category ->
                    jwtService.createToken(category.getCategoryName(), category.getName(), Role.OPERATOR.toString() ));
                        // category.getRole().name()));
    }
--------- */


    public void createShopping(Shopping shopping) {
        shopping.setRegistrationDate(LocalDateTime.now());
        this.shoppingRepository.save(shopping);
    }

    public Stream<Shopping> readAllByUserId(int userId) {
        return this.shoppingRepository.findAllByUserId(userId).stream();
    }


 /*   public Stream< Category > readAll(Role roleClaim) {
        return this.categoryRepository.findByRoleIn(authorizedRoles(roleClaim)).stream();
    }

  */





/*
    private void assertNoExistByMobile(String mobile) {
        if (this.categoryRepository.findByMobile(mobile).isPresent()) {
            throw new ConflictException("The mobile already exists: " + mobile);
        }
    }

 */
/*
    public Stream< Category > findByMobileAndFirstNameAndFamilyNameAndEmailAndDniContainingNullSafe(
            String mobile, String firstName, String familyName, String email, String dni, Role roleClaim) {
        return this.categoryRepository.findByMobileAndFirstNameAndFamilyNameAndEmailAndDniContainingNullSafe(
                mobile, firstName, familyName, email, dni, this.authorizedRoles(roleClaim)
        ).stream();
    }
 */
    /*
    public Category readByMobileAssured(String mobile) {
        return this.categoryRepository.findByMobile(mobile)
                .orElseThrow(() -> new NotFoundException("The mobile don't exist: " + mobile));
    }

     */
}
