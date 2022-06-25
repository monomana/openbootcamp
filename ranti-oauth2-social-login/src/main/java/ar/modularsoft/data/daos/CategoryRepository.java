package ar.modularsoft.data.daos;

import ar.modularsoft.data.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Integer > {


}
