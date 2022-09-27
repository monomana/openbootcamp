package ar.mds.ranti_core.infrastructure.api.resources;

import ar.mds.ranti_core.domain.services.BranchService;
import ar.mds.ranti_core.infrastructure.api.Rest;
import ar.mds.ranti_core.infrastructure.api.dtos.BranchDto;
import ar.mds.ranti_core.infrastructure.api.dtos.ProductsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Rest
@RequestMapping("${ranti.company}" + BranchResource.BRANCHES)
public class BranchResource {
    public static final String BRANCHES = "/branches";

    private final BranchService branchService;

    @Autowired
    public BranchResource(BranchService branchService) {
        this.branchService = branchService;
    }

    @GetMapping()
    public Flux<BranchDto> getAllBranches() {
        return Flux.fromStream( this.branchService.getAllBranches()
                .map(BranchDto::new));
    }

    @GetMapping("/paginate")
    public Mono<Page<BranchDto>> getAllBranchesPaginate(
            @RequestParam(defaultValue = "",required = false) String description,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "description") String sortField,
            @RequestParam(defaultValue = "true") boolean asc
    ){
        if(!asc)
            return Mono.just(  (branchService.findByDescriptionOrCode(description,
                    PageRequest.of(page, size, Sort.by(sortField).descending())).map(BranchDto::new)));
        return  Mono.just(  (branchService.findByDescriptionOrCode(description, PageRequest.of(page, size, Sort.by(sortField))).map(BranchDto::new)));
    }
}
