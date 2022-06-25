package ar.modularsoft.services;

import ar.modularsoft.data.daos.CategoryRepository;
import ar.modularsoft.data.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;


    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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


   /* public void createCategory(Category category, Role roleClaim) {
        if (!authorizedRoles(roleClaim).contains(category.getRole())) {
            throw new ForbiddenException("Insufficient role to create this category: " + category);
        }
        this.assertNoExistByMobile(category.getMobile());
        category.setRegistrationDate(LocalDateTime.now());
        this.categoryRepository.save(category);
    }
*/

 /*   public Stream< Category > readAll(Role roleClaim) {
        return this.categoryRepository.findByRoleIn(authorizedRoles(roleClaim)).stream();
    }

  */
    public Stream<Category> readAll() {
        return this.categoryRepository.findAll().stream();
    }




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
