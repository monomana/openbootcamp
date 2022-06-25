package ar.mds.ranti_core.infrastructure.api.dtos;

import ar.mds.ranti_core.domain.model.Cashier;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static java.math.BigDecimal.ZERO;

@Data
@NoArgsConstructor
public class CashierClosuresDto {
    private List<Cashier> cashiers;

    public CashierClosuresDto(List<Cashier> cashiers) {
        Cashier cashierTotals = Cashier.builder().id("TOTAL").initialCash(ZERO).cashSales(ZERO).cardSales(ZERO)
                .usedVouchers(ZERO).deposit(ZERO).withdrawal(ZERO).comment("").lostCard(ZERO).lostCash(ZERO)
                .finalCash(ZERO).openingDate(null).closureDate(null).build();
        cashiers.forEach(cashier -> {
            cashierTotals.setInitialCash(cashierTotals.getInitialCash().add(cashier.getInitialCash()));
            cashierTotals.setCashSales(cashierTotals.getCashSales().add(cashier.getCashSales()));
            cashierTotals.setCardSales(cashierTotals.getCardSales().add(cashier.getCardSales()));
            cashierTotals.setUsedVouchers(cashierTotals.getUsedVouchers().add(cashier.getUsedVouchers()));
            cashierTotals.setDeposit(cashierTotals.getDeposit().add(cashier.getDeposit()));
            cashierTotals.setWithdrawal(cashierTotals.getWithdrawal().add(cashier.getWithdrawal()));
            cashierTotals.setLostCard(cashierTotals.getLostCard().add(cashier.getLostCard()));
            cashierTotals.setLostCash(cashierTotals.getLostCash().add(cashier.getLostCash()));
            cashierTotals.setFinalCash(cashierTotals.getFinalCash().add(cashier.getFinalCash()));
        });
        cashiers.add(0, cashierTotals);
        this.cashiers = cashiers;
    }
}
