package ar.mds.ranti_core.infrastructure.mongodb.entities;

import ar.mds.ranti_core.domain.model.Messenger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class MessengerEntity {
    @Id
    private String id;
    private String fromUser;
    private String toUser;
    private String subject;
    private String text;
    private Boolean read;


    public MessengerEntity(Messenger messenger) {
        BeanUtils.copyProperties(messenger, this);
    }

    public MessengerEntity from(Messenger messenger) {
        BeanUtils.copyProperties(messenger, this, "id");
        return this;
    }

    public Messenger toMessenger() {
        Messenger messenger = new Messenger();
        BeanUtils.copyProperties(this, messenger);
        return messenger;
    }
}
