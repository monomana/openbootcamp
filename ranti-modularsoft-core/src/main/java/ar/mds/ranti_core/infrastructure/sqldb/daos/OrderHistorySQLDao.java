package ar.mds.ranti_core.infrastructure.sqldb.daos;

import ar.mds.ranti_core.domain.model.OrderHistorySQL;
import ar.mds.ranti_core.domain.model.OrderSQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderHistorySQLDao extends JpaRepository<OrderHistorySQL, Long> {
    @Query(
            value = "SELECT a.* FROM articulos a " +
                    "join articulos_proveedor ap on a.art_id = ap.ap_art_id " +
                    "WHERE ap.ap_prov_id = ?1 group by ap.ap_art_id",
            nativeQuery = true)
    List<OrderSQL> findByCompanyIdJoinAP(Integer id);

  OrderHistorySQL findById(Integer id);
}
