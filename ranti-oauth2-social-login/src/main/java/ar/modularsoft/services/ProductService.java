package ar.modularsoft.services;

import ar.modularsoft.data.daos.ProductRepository;
import ar.modularsoft.data.model.Role;
import ar.modularsoft.data.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class ProductService {

    private final ProductRepository productRepository;


    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;

    }
  /*  public Stream<Product> pages(Pageable pageable){
        return this.productRepository.findAll(pageable).stream();
    }

   */

   /* public Optional< String > loginWhitMobile(String mobile) {
        return this.productRepository.findByMobile(mobile)
                .map(product -> jwtService.createToken(product.getMobile(), product.getFirstName(), product.getRole().name()));
    } */

    /* --------------- RETURN TOKEN STRING BASE64
    public Optional< String > loginWithUserName(String username) {
LogManager.getLogger(username);
System.out.println(username);
        return this.productRepository.findByUserName(username)
                .map(product ->
                    jwtService.createToken(product.getUserName(), product.getName(), Role.OPERATOR.toString() ));
                        // product.getRole().name()));
    }
--------- */

   
   /* public void createUser(Product product, Role roleClaim) {
        if (!authorizedRoles(roleClaim).contains(product.getRole())) {
            throw new ForbiddenException("Insufficient role to create this product: " + product);
        }
        this.assertNoExistByMobile(product.getMobile());
        product.setRegistrationDate(LocalDateTime.now());
        this.productRepository.save(product);
    }
*/

 /*   public Stream< Product > readAll(Role roleClaim) {
        return this.productRepository.findByRoleIn(authorizedRoles(roleClaim)).stream();
    }

  */
    public Stream<Product> readAllByCompanyName(String company) {
        return this.productRepository.findAll().stream();
    }

   /* public Stream<Product> readAllByCompanyId(Integer companyId) {
        return this.productRepository.findByCompanyIdJoinAP(companyId)
                .stream();
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
        if (this.productRepository.findByMobile(mobile).isPresent()) {
            throw new ConflictException("The mobile already exists: " + mobile);
        }
    }

 */
/*
    public Stream< Product > findByMobileAndFirstNameAndFamilyNameAndEmailAndDniContainingNullSafe(
            String mobile, String firstName, String familyName, String email, String dni, Role roleClaim) {
        return this.productRepository.findByMobileAndFirstNameAndFamilyNameAndEmailAndDniContainingNullSafe(
                mobile, firstName, familyName, email, dni, this.authorizedRoles(roleClaim)
        ).stream();
    }
 */
    /*
    public Product readByMobileAssured(String mobile) {
        return this.productRepository.findByMobile(mobile)
                .orElseThrow(() -> new NotFoundException("The mobile don't exist: " + mobile));
    }

     */
}
