package ar.modularsoft.api.dtos;

import ar.modularsoft.data.model.Company;
import ar.modularsoft.data.model.CompanyMenu;
import ar.modularsoft.data.model.CompanyMenuId;
import ar.modularsoft.data.model.Menu;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyMenuRawDto {

    private CompanyMenuId id;
    private Company company;
    private Menu menu;
    private boolean state;

    public CompanyMenuRawDto(CompanyMenu companyMenu) {
        BeanUtils.copyProperties(companyMenu, this);
    }

    public CompanyMenu toCompanyMenu(CompanyMenuRawDto companyMenuDto) {
        CompanyMenu menu = new CompanyMenu();
        BeanUtils.copyProperties(companyMenuDto, menu);
        return menu;
    }

  //  public CompanyMenuDto(Company company) {
    //    System.out.print(company.getCategory());

    /*    this.category=CategoryDto.builder()
                .id(company.getCategory().getId())
                .code(company.getCategory().getCode())
                .description(company.getCategory().getDescription())
                .icon(company.getCategory().getIcon())
                .build();
        BeanUtils.copyProperties(company, this);
*/
       
  //  }


    /*
    public static CompanyMenuDto nameAndImage(Company company) {
        return CompanyMenuDto.builder().companyName(company.getCompanyName()).image(company.getImage()).build();
    }

     */

/*
    public Company toCompany() {
        this.doDefault();
        Company company = new Company();
        BeanUtils.copyProperties(this, company);
       // company.setPassword(new BCryptPasswordEncoder().encode(this.password));
        return company;
    }

 */
}
