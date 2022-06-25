package ar.modularsoft.api.dtos;

import ar.modularsoft.data.model.Category;
import ar.modularsoft.data.model.Role;
import ar.modularsoft.data.model.Company;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyDto {

    private int id;
    @NotNull
    @NotBlank
    private String companyName;
    private String image;
    private String email;
    // private int categoryId;
    private String endpoint;
    private int port;
    private String ip;
    private String protocol;
    private String latitude;
    private String longitude;
/*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")

 */
    private CategoryDto category;


   // private int state;

   

    public CompanyDto(Company company) {
    //    System.out.print(company.getCategory());

        this.category=CategoryDto.builder()
                .id(company.getCategory().getId())
                .code(company.getCategory().getCode())
                .description(company.getCategory().getDescription())
                .build();
        BeanUtils.copyProperties(company, this);

       
    }
    public static CompanyDto nameAndImage(Company company) {
        return CompanyDto.builder().companyName(company.getCompanyName()).image(company.getImage()).build();
    }


    public void doDefault() {
        if (Objects.isNull(image)) {
            image = "replace with uri image";
        }
    /*    if (Objects.isNull(role)) {
            this.role = Role.CUSTOMER;
        }

     */
      /*  if (Objects.isNull(state)) {
            this.state = 1;
        }

       */
    }

    public Company toCompany() {
        this.doDefault();
        Company company = new Company();
        BeanUtils.copyProperties(this, company);
       // company.setPassword(new BCryptPasswordEncoder().encode(this.password));
        return company;
    }
}
