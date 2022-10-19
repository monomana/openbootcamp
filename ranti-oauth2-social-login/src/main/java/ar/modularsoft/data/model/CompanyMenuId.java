package ar.modularsoft.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@Embeddable
public class CompanyMenuId implements Serializable {
    private static final long serialVersionUID = -8648616631835464885L;
    @Column(name = "company_id", nullable = false)
    private Integer companyId;

    @Column(name = "menu_id", nullable = false)
    private Integer menuId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CompanyMenuId entity = (CompanyMenuId) o;
        return Objects.equals(this.companyId, entity.companyId) &&
                Objects.equals(this.menuId, entity.menuId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, menuId);
    }

}