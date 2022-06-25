package ar.mds.ranti_core.infrastructure.mongodb.entities;

import ar.mds.ranti_core.domain.model.TechnicalSupportRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document
public class TechnicalSupportRequestEntity {
    @Id
    private String identifier;
    private String request;
    private List<TechnicalSupportAnswerEntity> answers;
    private Boolean resolved;

    public TechnicalSupportRequestEntity(TechnicalSupportRequest technicalSupportRequest) {
        BeanUtils.copyProperties(technicalSupportRequest, this);
        this.answers = new ArrayList<>();
    }

    public TechnicalSupportRequest toTechnicalSupportRequest() {
        TechnicalSupportRequest technicalSupportRequest = new TechnicalSupportRequest();
        BeanUtils.copyProperties(this, technicalSupportRequest);
        if (Objects.nonNull(this.getAnswers())) {
            technicalSupportRequest.setAnswers(this.getAnswers().stream()
                    .map(TechnicalSupportAnswerEntity::toAnswer)
                    .collect(Collectors.toList())
            );
        }
        return technicalSupportRequest;
    }
}
