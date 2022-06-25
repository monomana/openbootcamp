package ar.mds.ranti_core.domain.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TechnicalSupportAnswer {
    private String id;
    @NotNull
    @NotBlank
    private String answer;
    @NotNull
    @NotBlank
    private LocalDateTime dateSent;
}
