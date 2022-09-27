package ar.mds.ranti_core.domain.services;

import ar.mds.ranti_core.domain.model.Product;
import ar.mds.ranti_core.infrastructure.sqldb.daos.BranchDao;

import ar.mds.ranti_core.domain.model.BranchSQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Service
public class BranchService {

    private final BranchDao branchDao;

    @Autowired
    public BranchService(BranchDao branchDao) {
        this.branchDao = branchDao;
    }


    public Stream<BranchSQL> getAllBranches() {
        return this.branchDao.findAll().stream();
    }

    @Transactional
    public Page<BranchSQL> pages(Pageable pageable){
        return this.branchDao.findAll(pageable);
    }

    @Transactional
    public Page<BranchSQL> findByDescriptionOrCode(String description, Pageable page){
        return this.branchDao.findByDescriptionContainingIgnoreCase( description,page);
    }
  /*
    public Stream<Product> readAllByCompanyId(Integer branchId) {
        return this.branchDao.findByBranchIdJoinAP(branchId)
                .stream();
    }
   */
}
