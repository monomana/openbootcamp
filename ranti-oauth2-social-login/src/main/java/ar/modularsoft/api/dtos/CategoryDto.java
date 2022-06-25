package ar.modularsoft.api.dtos;

import ar.modularsoft.data.model.Category;
import ar.modularsoft.data.model.Company;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDto {

    private int id;
    @NotNull
    @NotBlank
    private String description;
    private String code;
    private String icon;



    public CategoryDto(Category category) {
        BeanUtils.copyProperties(category, this);

       
    }

}
