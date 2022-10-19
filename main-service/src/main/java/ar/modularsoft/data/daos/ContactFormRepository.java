package ar.modularsoft.data.daos;

import ar.modularsoft.data.model.ContactForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactFormRepository extends JpaRepository<ContactForm,Integer> {

}
