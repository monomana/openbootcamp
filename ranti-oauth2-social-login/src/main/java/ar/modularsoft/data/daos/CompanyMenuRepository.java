package ar.modularsoft.data.daos;

import ar.modularsoft.data.model.CompanyMenu;
import ar.modularsoft.data.model.CompanyMenuId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface CompanyMenuRepository extends JpaRepository<CompanyMenu, CompanyMenuId> {

    @Query(
            value = "SELECT * FROM company_menu WHERE company_id = ?1 AND state=1",
            nativeQuery = true)
    List<CompanyMenu> findMenuByCompanyId(int id);

}
