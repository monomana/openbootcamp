package ar.mds.ranti_core.infrastructure.mongodb.entities;

import ar.mds.ranti_core.domain.model.ComplaintState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Document
public class ComplaintEntity {
    @Id
    private String id;
    private String mobile;
    private String barcode;
    private String description;
    private ComplaintState state;
    private String reply;
    private LocalDateTime registrationDate;
}
