package ar.modularsoft.data.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmailBody {
    private String content;
    private String email;
    private String subject;

}
