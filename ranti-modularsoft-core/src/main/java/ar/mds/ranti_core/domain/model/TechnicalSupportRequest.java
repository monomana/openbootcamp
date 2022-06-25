package ar.mds.ranti_core.domain.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TechnicalSupportRequest {
    private String identifier;
    @NotNull
    @NotBlank
    private String request;
    private List<TechnicalSupportAnswer> answers;
    private Boolean resolved;

    public static TechnicalSupportRequest ofRequestText(TechnicalSupportRequest technicalSupportRequest) {
        return TechnicalSupportRequest.builder()
                .identifier(technicalSupportRequest.getIdentifier())
                .request(technicalSupportRequest.getRequest())
                .answers(technicalSupportRequest.getAnswers())
                .resolved(technicalSupportRequest.getResolved())
                .build();
    }
}
