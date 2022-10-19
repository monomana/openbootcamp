package ar.modularsoft.api.resources;

import ar.modularsoft.data.model.EmailBody;
import ar.modularsoft.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping(EmailResource.EMAIL)
//@Api( tags = "Email")
public class EmailResource {
    public static final String EMAIL = "/sendmail";
    private final EmailService emailService;

    @Autowired
    public EmailResource(EmailService emailService) {
        this.emailService = emailService;
    }


    //   @ApiOperation(value = "Envia email de confirmacion de compra a los usuarios")

    @PostMapping
    public void sendEmail(@Valid @RequestBody EmailBody emailBody) {
        this.emailService.sendEmail(emailBody);
    }


}
