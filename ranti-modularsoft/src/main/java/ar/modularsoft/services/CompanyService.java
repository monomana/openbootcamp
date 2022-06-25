package ar.modularsoft.services;

import ar.modularsoft.api.dtos.CompanyDto;
import ar.modularsoft.data.daos.CompanyRepository;
import ar.modularsoft.data.model.Role;
import ar.modularsoft.data.model.Company;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;


    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

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
    public Stream<Company> readAll() {
        return this.companyRepository.findAll().stream();
    }

    public Stream<Company> getCompanyByCategory(int categoryId) {
        return this.companyRepository.findByCategoryId(categoryId).stream();
    }


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
