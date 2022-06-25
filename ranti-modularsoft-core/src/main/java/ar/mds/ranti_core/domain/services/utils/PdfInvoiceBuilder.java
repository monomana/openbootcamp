package ar.mds.ranti_core.domain.services.utils;

import ar.mds.ranti_core.domain.model.Invoice;

import java.time.format.DateTimeFormatter;

public class PdfInvoiceBuilder {

    private static final String PATH = "/tpv-pdfs/invoices/";
    private static final String FILE = "invoice-";

    public byte[] generateInvoice(Invoice invoice) {
        PdfCoreBuilder pdf = new PdfCoreBuilder(PATH, FILE + invoice.getIdentity());
        pdf.head();
        pdf.paragraphEmphasized("Invoice Identity : " + invoice.getIdentity());
        pdf.paragraph(invoice.getCreationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        pdf.paragraphEmphasized("Details");
        pdf.paragraph("Ticket ID: " + invoice.getTicket().getId());
        pdf.paragraph("Base Tax : " + invoice.getBaseTax() + "€");
        pdf.paragraph("Tax Value : " + invoice.getTaxValue() + "€");

        pdf.paragraphEmphasized("Contact Details");
        pdf.paragraph("Mobile: " + invoice.getUser().getMobile());
        pdf.paragraph("Full name: " + invoice.getUser().getFirstName() + " " + invoice.getUser().getFamilyName());
        pdf.paragraph("DNI: " + invoice.getUser().getDni());
        pdf.paragraph("Address: " + invoice.getUser().getAddress());



        return pdf.foot().build();
    }
}
