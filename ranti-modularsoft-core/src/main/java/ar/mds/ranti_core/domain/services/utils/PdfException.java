package ar.mds.ranti_core.domain.services.utils;

public class PdfException extends RuntimeException {
    private static final String DESCRIPTION = "File exception";

    public PdfException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
