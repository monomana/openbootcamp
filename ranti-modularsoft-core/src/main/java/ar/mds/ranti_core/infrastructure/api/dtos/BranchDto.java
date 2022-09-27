package ar.mds.ranti_core.infrastructure.api.dtos;

import ar.mds.ranti_core.domain.model.BranchSQL;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchDto {

    private int id;
    private String description;

    public BranchDto (BranchSQL branchSQL) {
        BeanUtils.copyProperties(branchSQL, this);

    }


}
