package ar.modularsoft.services;

import ar.modularsoft.data.daos.CompanyMenuRepository;
import ar.modularsoft.data.model.CompanyMenu;
import ar.modularsoft.data.model.CompanyMenuId;
import ar.modularsoft.data.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class CompanyMenuService {

    private final CompanyMenuRepository companyMenuRepository;

    @Autowired
    public CompanyMenuService(CompanyMenuRepository companyMenuRepository) {
        this.companyMenuRepository = companyMenuRepository;
    }

    public Stream<CompanyMenu> readAll() {
        return this.companyMenuRepository.findAll().stream();
    }

    public Stream<CompanyMenu> getCompanyMenuById(int companyId) {
        this.companyMenuRepository.findMenuByCompanyId(companyId).stream().forEach(companyMenu -> System.out.println( companyMenu));
        return this.companyMenuRepository.findMenuByCompanyId(companyId).stream();
    }

    public CompanyMenu save(CompanyMenu menu){
        return companyMenuRepository.save(menu);
    }

    public void deleteById(CompanyMenuId id) {
         companyMenuRepository.deleteById(id);
    }


   /*
    public Page<CompanyMenu> getCompanyByNameContains(String companyName, Pageable page) {
        return this.companyMenuRepository.findByCompanyNameContainsIgnoreCase(companyName,page);
    }

    */

   /* public Optional< String > loginWhitMobile(String mobile) {
        return this.companyRepository.findByMobile(mobile)
                .map(company -> jwtService.createToken(company.getMobile(), company.getFirstName(), company.getRole().name()));
    } */

    /* --------------- RETURN TOKEN STRING BASE64
    public Optional< String > loginWithCompanyName(String companyname) {
LogManager.getLogger(companyname);
System.out.println(companyname);
        return this.companyRepository.findByCompanyName(companyname)
                .map(company ->
                    jwtService.createToken(company.getCompanyName(), company.getName(), Role.OPERATOR.toString() ));
                        // company.getRole().name()));
    }
--------- */


   /* public void createCompany(Company company, Role roleClaim) {
        if (!authorizedRoles(roleClaim).contains(company.getRole())) {
            throw new ForbiddenException("Insufficient role to create this company: " + company);
        }
        this.assertNoExistByMobile(company.getMobile());
        company.setRegistrationDate(LocalDateTime.now());
        this.companyRepository.save(company);
    }
*/

 /*   public Stream< Company > readAll(Role roleClaim) {
        return this.companyRepository.findByRoleIn(authorizedRoles(roleClaim)).stream();
    }

  */



/*
    private void assertNoExistByMobile(String mobile) {
        if (this.companyRepository.findByMobile(mobile).isPresent()) {
            throw new ConflictException("The mobile already exists: " + mobile);
        }
    }

 */
/*
    public Stream< Company > findByMobileAndFirstNameAndFamilyNameAndEmailAndDniContainingNullSafe(
            String mobile, String firstName, String familyName, String email, String dni, Role roleClaim) {
        return this.companyRepository.findByMobileAndFirstNameAndFamilyNameAndEmailAndDniContainingNullSafe(
                mobile, firstName, familyName, email, dni, this.authorizedRoles(roleClaim)
        ).stream();
    }
 */
    /*
    public Company readByMobileAssured(String mobile) {
        return this.companyRepository.findByMobile(mobile)
                .orElseThrow(() -> new NotFoundException("The mobile don't exist: " + mobile));
    }

     */
}
