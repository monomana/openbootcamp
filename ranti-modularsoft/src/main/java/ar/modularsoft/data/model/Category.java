package ar.modularsoft.data.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Builder
@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "company_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int id;
    @NotNull
    @NotBlank
    private String description;
    private String abrev;
    private String code;
    private boolean state;
    private String icon;

}


