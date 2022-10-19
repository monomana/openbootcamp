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

import javax.persistence.Column;

// @Data
// @NoArgsConstructor
@Builder
// @AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyMenuCRUDDto {

    // private CompanyMenuId id;
    private Integer companyId;
    private Integer menuId;
    private boolean state;

//    public CompanyMenuCRUDDto(CompanyMenu companyMenu) {
//        BeanUtils.copyProperties(companyMenu, this);
//    }

    public CompanyMenu toCompanyMenuCRUD(CompanyMenuCRUDDto companyMenuCRUDDto) {
        CompanyMenu menu = new CompanyMenu();
       // BeanUtils.copyProperties(companyMenuCRUDDto, menu);
        CompanyMenuId companyMenuId = new CompanyMenuId();
        companyMenuId.setCompanyId(companyMenuCRUDDto.companyId);
        companyMenuId.setMenuId(companyMenuCRUDDto.menuId);
        menu.setId(companyMenuId);
//        menu.setCompany(null);
//        menu.setMenu(null);
        menu.setState(companyMenuCRUDDto.state);
     //   CompanyMenuDto companymenuDto;

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
