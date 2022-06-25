package ar.mds.ranti_core.domain.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static ar.mds.ranti_core.domain.model.CashMovementType.DEPOSIT;
import static ar.mds.ranti_core.domain.model.CashMovementType.WITHDRAWAL;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CashierTest {

    @Test
    void testAddCashMovement() {
        Cashier cashier = Cashier.builder().deposit(new BigDecimal(20)).withdrawal(new BigDecimal(10)).build();
        CashMovement deposit = CashMovement.builder().cash(new BigDecimal(100)).type(DEPOSIT).build();
        CashMovement withdrawal = CashMovement.builder().cash(new BigDecimal(50)).type(WITHDRAWAL).build();
        cashier.addCashMovement(deposit);
        cashier.addCashMovement(withdrawal);
        assertEquals(new BigDecimal(120), cashier.getDeposit());
        assertEquals(new BigDecimal(60), cashier.getWithdrawal());
    }
}
