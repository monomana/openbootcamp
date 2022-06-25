package ar.modularsoft.data.daos;


import ar.modularsoft.data.model.Shopping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public interface ShoppingRepository extends JpaRepository<Shopping, Integer > {


    List<Shopping> findAllByUserId(Integer userId);
}
