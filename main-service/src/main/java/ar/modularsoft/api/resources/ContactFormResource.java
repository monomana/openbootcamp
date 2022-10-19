package ar.modularsoft.api.resources;

import ar.modularsoft.api.dto.ContactFormDto;

import ar.modularsoft.services.ContactFormService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Stream;

@Slf4j
@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RequestMapping(ContactFormResource.CONTACT_FORM)
public class ContactFormResource {

    public static final String CONTACT_FORM = "/contact-form";

    private final ContactFormService contactFormService;

    @Autowired
    public ContactFormResource(ContactFormService contactFormService) {
        this.contactFormService = contactFormService;
    }

    // @ApiOperation(value = "Obtiene listado de contact de navegacion")
    @GetMapping()
    public Stream<ContactFormDto> getContact(
    ) {
        return contactFormService.readAll()
                .map(ContactFormDto::new);
    }

    //    @ApiOperation(value = "Agrega un contact de navegacion | Add navigation contact")
    @PostMapping()
    public ResponseEntity<?> saveContact(@RequestBody ContactFormDto contactFormDto) {
        try {

            contactFormService.save(contactFormDto.toContact(contactFormDto));
            return ResponseEntity.ok(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Exception Ocurred", e);
            return new ResponseEntity<>("Error to save contact", HttpStatus.BAD_REQUEST);
        }
    }

    //    @ApiOperation(value = "Actualiza contact a la emrpesa | Update contact to company")
    @PutMapping()
    public ResponseEntity<?> update(@RequestBody ContactFormDto contactFormDto) {
        try {
            contactFormService.save(contactFormDto.toContact(contactFormDto));
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception Ocurred", e);
            return new ResponseEntity<>("Error to update contact message", HttpStatus.BAD_REQUEST);
        }
    }

    //    @ApiOperation(value = "Borra contact a la emrpesa | Delete contact to company")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        try {
            contactFormService.deleteById(id);
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception Ocurred", e);
            return new ResponseEntity<>("Error to delete contact message", HttpStatus.BAD_REQUEST);
        }
    }

}
