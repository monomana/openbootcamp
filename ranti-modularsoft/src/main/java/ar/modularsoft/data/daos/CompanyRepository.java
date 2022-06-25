package ar.modularsoft.data.daos;

import ar.modularsoft.data.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface CompanyRepository extends JpaRepository<Company, Integer > {

    @Query(
            value = "SELECT c.* FROM company c " +
                    "join company_category cc on c.id = c.id " +
                    "WHERE  c.category_id = ?1",
            nativeQuery = true)
    List<Company> findByCategoryId(Integer categoryId);

}
