package ar.modularsoft.data.daos;

import ar.modularsoft.data.model.Product;
import ar.modularsoft.data.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

  //  Optional<Page<Product>> pages(Pageable pageable);

   /* @Query(
            value = "SELECT a.* FROM articulos a " +
                    "join articulos_proveedor ap on a.art_id = ap.ap_art_id " +
                    "WHERE ap.ap_prov_id = ?1 group by ap.ap_art_id",
            nativeQuery = true)
    List<Product> findByCompanyIdJoinAP(Integer id);

    */
}
