package ar.mds.ranti_core.infrastructure.sqldb.daos;

import ar.mds.ranti_core.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Long> {
    @Query(
            value = "SELECT a.* FROM articulos a " +
                    "join articulos_proveedor ap on a.art_id = ap.ap_art_id " +
                    "WHERE ap.ap_prov_id = ?1 group by ap.ap_art_id",
            nativeQuery = true)
    List<Product> findByCompanyIdJoinAP(Integer id);
    List<Product> findByDescriptionLikeOrCodeLike(String description,String code);
    // List<Product> findByDescriptionContaining(String description);
    Page<Product> findByDescriptionContainingIgnoreCase(String description,Pageable page);
    List<Product> findByDescriptionContainingOrCodeContaining(String description,String code);

  //  Page<Product> pages(Pageable pageable);

  //  Page<Product> findByDescriptionOrCode(Pageable pageable);
}
