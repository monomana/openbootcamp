package ar.modularsoft.data.daos;


import ar.modularsoft.data.model.Shopping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public interface ShoppingRepository extends JpaRepository<Shopping, Integer > {


    List<Shopping> findAllByUserId(Integer userId);
    Page<Shopping> findAllByUserIdAndState(Integer userId, int state, Pageable page);
    Page<Shopping> findAllByUserIdAndCompanyId(Integer userId,Integer companyId, Pageable page);
}
