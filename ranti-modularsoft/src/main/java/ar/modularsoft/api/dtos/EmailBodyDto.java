package ar.modularsoft.api.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmailBodyDto {
    private String content;
    private String email;
    private String subject;

}
