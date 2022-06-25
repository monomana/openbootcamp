package ar.mds.ranti_core.domain.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Messenger {
    @Id
    private String id;
    @NotBlank
    private String fromUser;
    @NotBlank
    private String toUser;
    @NotBlank
    private String subject;
    @NotBlank
    private String text;
    @NotNull
    private Boolean read;
}
