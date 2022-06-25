package ar.mds.ranti_core.infrastructure.mongodb.daos;

import ar.mds.ranti_core.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductReactive extends JpaRepository<Product, Long> {
    @Query(
            value = "SELECT a.* FROM articulos a " +
                    "join articulos_proveedor ap on a.art_id = ap.ap_art_id " +
                    "WHERE ap.ap_prov_id = ?1 group by ap.ap_art_id",
            nativeQuery = true)
    List<Product> findByCompanyIdJoinAP(Integer id);
}
