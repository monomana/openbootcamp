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

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyMenuDto {

   //  private CompanyMenuId id;
   //  private Company company;
    private Menu menu;
    private boolean state;

    public CompanyMenuDto(CompanyMenu companyMenu) {
        BeanUtils.copyProperties(companyMenu, this);
    }

    public CompanyMenu toCompanyMenu(CompanyMenuDto companyMenuDto) {
        CompanyMenu menu = new CompanyMenu();
        BeanUtils.copyProperties(companyMenuDto, menu);
        return menu;
    }

    public static MenuDto toMenuDto(CompanyMenu companyMenu) {
        MenuDto menuDto = new MenuDto(companyMenu.getMenu());
        return menuDto;
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
