package ar.modularsoft.services;

import ar.modularsoft.data.daos.ContactFormRepository;
import ar.modularsoft.data.model.ContactForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class ContactFormService {
    private final ContactFormRepository contactFormRepository;

    @Autowired
    public ContactFormService(ContactFormRepository contactFormRepository) {
        this.contactFormRepository = contactFormRepository;
    }

    public Stream<ContactForm> readAll() {
        return this.contactFormRepository.findAll().stream();
    }

    public ContactForm save(ContactForm contact){
        return contactFormRepository.save(contact);
    }

    public Optional<ContactForm> getContactById(int id) {
        return this.contactFormRepository.findById(id);
    }

    public void deleteById(Integer id) {
        contactFormRepository.deleteById(id);
    }

}
