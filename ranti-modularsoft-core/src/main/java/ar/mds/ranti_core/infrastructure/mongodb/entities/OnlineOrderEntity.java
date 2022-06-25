package ar.mds.ranti_core.infrastructure.mongodb.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class OnlineOrderEntity {
    @Id
    private String id;
    @Indexed(unique = true)
    private String reference;
    private LocalDateTime deliveryDate;
    @DBRef
    private TicketEntity ticketEntity;
}
