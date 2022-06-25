package ar.mds.ranti_core.domain.services.utils;

import ar.mds.ranti_core.domain.model.Budget;
import ar.mds.ranti_core.domain.model.Property;
import ar.mds.ranti_core.domain.model.ShoppingState;

import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;


public class PdfBudgetBuilder {

    private static final String[] TABLE_COLUMNS_HEADERS = {"Desc.", "Ud.", "Dto.%", "€", "E."};
    private static final float[] TABLE_COLUMNS_SIZES_TICKETS = {90, 15, 25, 35, 15};
    private static final String PATH = "/tpv-pdfs/budgets/";
    private static final String FILE = "budget-";
    private static final String BUDGETS = "/home/budgets/";

    public byte[] generateBudget(Budget budget) {

        PdfCoreBuilder pdf = new PdfCoreBuilder(PATH, FILE + budget.getReference());
        pdf.head();

        pdf.paragraphEmphasized("BUDGET " + budget.getReference());

        pdf.paragraphEmphasized(budget.getCreationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        PdfTableBuilder table = pdf.table(TABLE_COLUMNS_SIZES_TICKETS).tableColumnsHeader(TABLE_COLUMNS_HEADERS);
        budget.getShoppings().forEach(shopping -> {
            String state = (shopping.getState() != ShoppingState.COMMITTED && shopping.getAmount() > 0) ? "N" : "";
            String discount = "";
            if ((shopping.getDiscount().doubleValue() > 0.04) && !shopping.getBarcode().equals("1")) {
                discount = "" + shopping.getDiscount().setScale(1, RoundingMode.HALF_UP);
            }
            table.tableCells(shopping.getDescription(), "" + shopping.getAmount(), discount,
                    shopping.totalShopping().setScale(2, RoundingMode.HALF_UP) + "€", state);
        });

        table.tableColspanRight(budget.total().setScale(2, RoundingMode.HALF_UP) + "€").buildTable();

        pdf.qrCode(Property.getProperty().getMiwTpv() + BUDGETS + budget.getReference());

        return pdf.foot().build();
    }
}
