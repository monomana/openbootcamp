package ar.mds.ranti_core.domain.services;

import ar.mds.ranti_core.domain.model.Product;
import ar.mds.ranti_core.infrastructure.sqldb.daos.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
public class ProductService {

    private final ProductDao productDao;


    @Autowired
    public ProductService(ProductDao productDao) {
        this.productDao = productDao;

    }


 public Stream<Product> getAllProducts() {
     return this.productDao.findAll().stream();
 }
    public Stream<Product> readAllByCompanyName(String company) {
        return this.productDao.findAll().stream();
    }

    public Stream<Product> readAllByCompanyId(Integer companyId) {
        return this.productDao.findByCompanyIdJoinAP(companyId)
                .stream();
    }

    public Stream<Product> getOfertas( ) {
        return this.productDao.findByCompanyIdJoinAP(1)
                .stream();
    }

    @Transactional
    public Page<Product> pages(Pageable pageable){
        return this.productDao.findAll(pageable);
    }
    /*
     @Transactional
    public Page<Product> findByDescriptionOrCode(Pageable pageable){
        return this.productDao.findByDescriptionOrCode(pageable);
    }
     */
    @Transactional
    public Page<Product> findByDescriptionOrCode(String description, Pageable page){
        return this.productDao.findByDescriptionContainingIgnoreCase( description,page);
    }
/*
    @Transactional
    public Stream<Product> findByDescriptionOrCode(String description,Pageable page){
        return this.productDao.findByDescriptionContainingIgnoreCase( description,page).stream();
    }

 */
}
