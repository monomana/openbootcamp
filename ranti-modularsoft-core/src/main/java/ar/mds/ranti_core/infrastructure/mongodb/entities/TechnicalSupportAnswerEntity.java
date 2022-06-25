package ar.mds.ranti_core.infrastructure.mongodb.entities;

import ar.mds.ranti_core.domain.model.TechnicalSupportAnswer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TechnicalSupportAnswerEntity {
    private String id;
    private String answer;
    private LocalDateTime dateSent;

    public TechnicalSupportAnswer toAnswer() {
        TechnicalSupportAnswer technicalSupportAnswer = new TechnicalSupportAnswer();
        BeanUtils.copyProperties(this, technicalSupportAnswer);
        return technicalSupportAnswer;
    }
}
