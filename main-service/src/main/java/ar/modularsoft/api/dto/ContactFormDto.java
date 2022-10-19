package ar.modularsoft.api.dto;

import ar.modularsoft.data.model.ContactForm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContactFormDto {
    private String name;
    private String email;
    private String phone;
    private String message;
    private String company;

    public ContactFormDto(ContactForm contactForm){
        BeanUtils.copyProperties(contactForm,this);
    }


    public ContactForm toContact(ContactFormDto contactFormDto) {
        ContactForm contactForm = new ContactForm();
        BeanUtils.copyProperties(contactFormDto,contactForm);
        return contactForm;
    }
}
