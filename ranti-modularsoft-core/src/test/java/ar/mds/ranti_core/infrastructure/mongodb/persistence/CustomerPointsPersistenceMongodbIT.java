package ar.mds.ranti_core.infrastructure.mongodb.persistence;

import ar.mds.ranti_core.TestConfig;
import ar.mds.ranti_core.domain.model.CustomerPoints;
import ar.mds.ranti_core.domain.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestConfig
public class CustomerPointsPersistenceMongodbIT {

    @Autowired
    CustomerPointsPersistenceMongodb customerPointsPersistenceMongodb;

    @Test
    void testCreate() {
        CustomerPoints customerPoints = CustomerPoints.builder()
                .id(54)
                .value(58)
                .lastDate(LocalDate.of(2022, 12, 24))
                .user(User.builder().mobile("888965321").build())
                .build();

        StepVerifier
                .create(this.customerPointsPersistenceMongodb.create(customerPoints))
                .expectNextMatches(gotten -> {
                    assertNotNull(gotten.getId());
                    assertEquals(58, gotten.getValue());
                    assertNotNull(gotten.getLastDate());
                    assertNotNull(gotten.getUser());
                    assertEquals("888965321", gotten.getUser().getMobile());
                    return true;
                })
                .expectComplete()
                .verify();
    }

    @Test
    void testRead() {
        CustomerPoints customerPoints = CustomerPoints.builder()
                .id(54)
                .value(58)
                .lastDate(LocalDate.of(2019, 12, 24))
                .user(User.builder().mobile("888965321").build())
                .build();

        StepVerifier
                .create(this.customerPointsPersistenceMongodb.create(customerPoints))
                .then(() -> this.customerPointsPersistenceMongodb.read("888965321"))
                .expectNextMatches(gotten -> {
                    assertNotNull(gotten.getUser());
                    assertEquals(customerPoints.getUser(), gotten.getUser());
                    assertNotNull(gotten.getId());
                    assertEquals(58, gotten.getValue());
                    assertEquals(LocalDate.of(2019, 12, 24), gotten.getLastDate());
                    return true;
                })
                .expectComplete()
                .verify();

    }

    @Test
    void testFindByMobileIfNotExistsReturnEmptyCustomerPoints() {

        StepVerifier
                .create(this.customerPointsPersistenceMongodb.findByMobileIfNotExistsReturnEmptyCustomerPoints("8484"))
                .expectNextMatches(gotten -> {
                    assertNotNull(gotten.getUser());
                    assertNotNull(gotten.getId());
                    assertEquals(0, gotten.getValue());
                    return true;
                })
                .expectComplete()
                .verify();

    }

    @Test
    void testUpdate() {

        CustomerPoints customerPoints = CustomerPoints.builder()
                .id(54)
                .value(58)
                .lastDate(LocalDate.of(2022, 12, 24))
                .user(User.builder().mobile("888965321").build())
                .build();

        StepVerifier
                .create(this.customerPointsPersistenceMongodb.create(customerPoints))
                .expectNextMatches(gotten -> {
                    assertNotNull(gotten.getId());
                    return true;
                })
                .expectComplete()
                .verify();

        customerPoints.setValue(88);
        LocalDate tempDate = LocalDate.now();
        customerPoints.setLastDate(tempDate);

        StepVerifier
                .create(this.customerPointsPersistenceMongodb.update("888965321", customerPoints))
                .expectNextMatches(gotten -> {
                    assertEquals(tempDate, gotten.getLastDate());
                    assertEquals(88, gotten.getValue());
                    assertNotNull(gotten.getUser());
                    assertEquals(54, gotten.getId());
                    return true;
                })
                .expectComplete()
                .verify();

    }

    @Test
    void testFindByMobileAndDate() {
        CustomerPoints customerPoints = CustomerPoints.builder()
                .id(54)
                .value(58)
                .lastDate(LocalDate.of(2019, 2, 2))
                .user(User.builder().mobile("888965321").build())
                .build();

        StepVerifier
                .create(this.customerPointsPersistenceMongodb.create(customerPoints))
                .expectNextMatches(gotten -> {
                    assertNotNull(gotten.getUser());
                    assertEquals(customerPoints.getUser(), gotten.getUser());
                    assertNotNull(gotten.getId());
                    assertEquals(58, gotten.getValue());
                    assertEquals(LocalDate.of(2019, 2, 2), gotten.getLastDate());
                    return true;
                })
                .expectComplete()
                .verify();


        StepVerifier
                .create(this.customerPointsPersistenceMongodb.findByMobileAndDate("888965321", LocalDate.now().minusYears(1), LocalDate.now()))
                .expectNextMatches(gotten -> {
                    assertEquals(0, gotten.getValue());
                    assertNotNull(gotten.getUser());
                    assertEquals(LocalDate.of(2019, 2, 2), gotten.getLastDate());
                    return true;
                })
                .expectComplete()
                .verify();

    }

}
