package ar.modularsoft.data.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Builder
@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int id;
    //@Column(name = "CLI_NAME")
    @NotNull
    @NotBlank
    private String companyName;
    private String image;

    private String email;
    // private int categoryId; // si habiltas este campo debes deshabiltar el join
    private String endpoint;
    private int port;
    private String ip;
    private String protocol;
    private Date createdAt;
    private Date updatedAt;
    private String latitude;
    private String longitude;
    private boolean state;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;


}


