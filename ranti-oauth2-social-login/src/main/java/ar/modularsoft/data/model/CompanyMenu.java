package ar.modularsoft.data.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Builder
@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "company_menu")
public class CompanyMenu {

    @EmbeddedId
    private CompanyMenuId id;

  //  @MapsId("companyId")
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "company_id", insertable = false,updatable = false)
    private Company company;

   // @MapsId("menuId")
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "menu_id", insertable = false,updatable = false)
    private Menu menu;

    @Column(name = "state")
    private Boolean state;

}


