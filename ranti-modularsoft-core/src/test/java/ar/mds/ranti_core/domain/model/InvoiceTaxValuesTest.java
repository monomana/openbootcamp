package ar.mds.ranti_core.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvoiceTaxValuesTest {

    @Test
    void testGetTaxValues() {
        InvoiceTaxValues invoiceTaxValues = new InvoiceTaxValues(Tax.GENERAL, new BigDecimal("20.00"));
        assertEquals(0, new BigDecimal("16.53").compareTo(invoiceTaxValues.getBaseTax()));
        assertEquals(0, new BigDecimal("3.47").compareTo(invoiceTaxValues.getTaxValue()));
        assertEquals(Tax.GENERAL, invoiceTaxValues.getTax());
        assertEquals(0, new BigDecimal("20.00").compareTo(invoiceTaxValues.getTotalShopping()));
    }
}
