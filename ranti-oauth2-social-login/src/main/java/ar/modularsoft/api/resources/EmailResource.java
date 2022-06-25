package ar.modularsoft.api.resources;

import ar.modularsoft.data.model.EmailBody;
import ar.modularsoft.data.model.Role;
import ar.modularsoft.services.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
@RestController
@RequestMapping(EmailResource.EMAIL)
@Api( tags = "Email")
public class EmailResource {
    public static final String EMAIL = "/sendmail";
    private final EmailService emailService;

    @Autowired
    public EmailResource(EmailService emailService) {
        this.emailService = emailService;
    }


    @ApiOperation(value = "Envia email de confirmacion de compra a los usuarios")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public void sendEmail(@Valid @RequestBody EmailBody emailBody) {
    this.emailService.sendEmail(emailBody);
    }


}
