package ar.modularsoft.services;

import ar.modularsoft.data.model.EmailBody;

public interface EmailPort {
    public boolean sendEmail(EmailBody emailBody);
}
