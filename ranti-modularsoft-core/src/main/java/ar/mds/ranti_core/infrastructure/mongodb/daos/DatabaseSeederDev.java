/*
package ar.mds.ranti_core.infrastructure.mongodb.daos;

import ar.mds.ranti_core.infrastructure.mongodb.daos.synchronous.*;
import ar.mds.ranti_core.infrastructure.mongodb.entities.*;
import ar.mds.ranti_core.domain.model.ShoppingState;
import ar.mds.ranti_core.domain.model.Tax;
import ar.mds.ranti_core.domain.model.TreeType;
import ar.mds.ranti_core.infrastructure.mongodb.daos.synchronous.*;
import ar.mds.ranti_core.infrastructure.mongodb.entities.*;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.math.BigDecimal.ZERO;

@Service // @Profile("dev")
public class DatabaseSeederDev {
    private final ArticleDao articleDao;
    private final ProviderDao providerDao;
    private final ArticlesTreeDao articlesTreeDao;
    private final TicketDao ticketDao;
    private final BudgetDao budgetDao;
    private final CashierDao cashierDao;
    private final OrderDao orderDao;
    private final InvoiceDao invoiceDao;
    private final ProviderInvoiceDao providerInvoiceDao;
    private final TechnicalSupportDao technicalSupportDao;
    private final CustomerDiscountDao customerDiscountDao;
    private final ShoppingCartDao cartDao;
    private final CreditLineDao creditLineDao;
    private final StockAuditDao stockAuditDao;
    private final ArticleLossDao articleLossDao;

    private final DatabaseStarting databaseStarting;

    @Autowired
    public DatabaseSeederDev(ArticleDao articleDao, ProviderDao providerDao, ArticlesTreeDao articlesTreeDao,
                             TicketDao ticketDao, CashierDao cashierDao, DatabaseStarting databaseStarting,
                             OrderDao orderDao, InvoiceDao invoiceDao, ProviderInvoiceDao providerInvoiceDao,
                             TechnicalSupportDao technicalSupportDao, CustomerDiscountDao customerDiscountDao,
                             BudgetDao budgetDao, ShoppingCartDao shoppingCartDao, CreditLineDao creditLineDao, StockAuditDao stockAuditDao, ArticleLossDao articleLossDao) {
        this.articleDao = articleDao;
        this.providerDao = providerDao;
        this.articlesTreeDao = articlesTreeDao;
        this.ticketDao = ticketDao;
        this.budgetDao = budgetDao;
        this.cashierDao = cashierDao;
        this.databaseStarting = databaseStarting;
        this.orderDao = orderDao;
        this.invoiceDao = invoiceDao;
        this.providerInvoiceDao = providerInvoiceDao;
        this.technicalSupportDao = technicalSupportDao;
        this.customerDiscountDao = customerDiscountDao;
        this.cartDao = shoppingCartDao;
        this.creditLineDao = creditLineDao;
        this.stockAuditDao = stockAuditDao;
        this.articleLossDao = articleLossDao;

        this.deleteAllAndInitializeAndSeedDataBase();
    }

    public void deleteAllAndInitializeAndSeedDataBase() {
        this.deleteAllAndInitialize();
        this.seedDataBaseJava();
    }

    private void deleteAllAndInitialize() {
        this.ticketDao.deleteAll();
        this.budgetDao.deleteAll();

        this.articleDao.deleteAll();

        this.providerInvoiceDao.deleteAll();

        this.providerDao.deleteAll();
        this.cashierDao.deleteAll();
        this.invoiceDao.deleteAll();
        this.orderDao.deleteAll();
        this.customerDiscountDao.deleteAll();
        this.creditLineDao.deleteAll();

        this.technicalSupportDao.deleteAll();
        this.cartDao.deleteAll();
        this.stockAuditDao.deleteAll();
        this.articleLossDao.deleteAll();

        LogManager.getLogger(this.getClass()).warn("------- Delete All -----------");
        this.databaseStarting.initialize();
    }

    private void seedDataBaseJava() {
        LogManager.getLogger(this.getClass()).warn("------- Initial Load from JAVA -----------");
        ProviderEntity[] providers = {
                ProviderEntity.builder().company("pro1").nif("12345678b").phone("9166666601")
                        .address("C/TPV-pro, 1").email("p1@gmail.com").note("p1").active(true).build(),
                ProviderEntity.builder().company("pro2").nif("12345678z").phone("9166666602")
                        .address("C/TPV-pro, 2").email("p2@gmail.com").active(false).build(),
                ProviderEntity.builder().company("pro3").nif("12345678e").phone("9166666603")
                        .address("C/TPV-pro, 3").email("p2@gmail.com").note("p3").active(true).build(),
                ProviderEntity.builder().company("pro4").nif("12345678h").phone("9166666604")
                        .address("C/TPV-pro, 4").email("p3@gmail.com").note("p4").active(true).build(),
        };
        this.providerDao.saveAll(List.of(providers));
        LogManager.getLogger(this.getClass()).warn("        ------- providers");
        ArticleEntity[] articles = {
                ArticleEntity.builder().barcode("8400000000017").reference("zz-falda-T2").description("Zarzuela - Falda T2")
                        .retailPrice(new BigDecimal("20")).stock(10).providerEntity(providers[0])
                        .registrationDate(LocalDateTime.now()).discontinued(false).build(),
                ArticleEntity.builder().barcode("8400000000024").reference("zz-falda-T4").description("Zarzuela - Falda T4")
                        .retailPrice(new BigDecimal("27.8")).stock(5).providerEntity(providers[0])
                        .registrationDate(LocalDateTime.now()).discontinued(false).build(),
                ArticleEntity.builder().barcode("8400000000031").reference("ref-a3").description("descrip-a3")
                        .retailPrice(new BigDecimal("10.12")).stock(8).tax(Tax.FREE).providerEntity(providers[0])
                        .registrationDate(LocalDateTime.now()).discontinued(false).build(),
                ArticleEntity.builder().barcode("8400000000048").reference("ref-a4").description("descrip-a4")
                        .retailPrice(new BigDecimal("0.23")).stock(1).tax(Tax.REDUCED).providerEntity(providers[0])
                        .registrationDate(LocalDateTime.now()).discontinued(false).build(),
                ArticleEntity.builder().barcode("8400000000055").reference("ref-a5").description("descrip-a5")
                        .retailPrice(new BigDecimal("0.23")).stock(0).tax(Tax.SUPER_REDUCED).providerEntity(providers[0])
                        .registrationDate(LocalDateTime.now()).discontinued(false).build(),
                ArticleEntity.builder().barcode("8400000000062").reference("ref-a6").description("descrip-a6")
                        .retailPrice(new BigDecimal("0.01")).stock(0).providerEntity(providers[1])
                        .registrationDate(LocalDateTime.now()).discontinued(true).build(),
                ArticleEntity.builder().barcode("8400000000079").reference("zz-polo-T2").description("Zarzuela - Polo T2")
                        .retailPrice(new BigDecimal("16")).stock(10).providerEntity(providers[0])
                        .registrationDate(LocalDateTime.now()).discontinued(false).build(),
                ArticleEntity.builder().barcode("8400000000086").reference("zz-polo-T4").description("Zarzuela - Polo T4")
                        .retailPrice(new BigDecimal("17.8")).stock(5).providerEntity(providers[0])
                        .registrationDate(LocalDateTime.now()).discontinued(false).build(),
                ArticleEntity.builder().barcode("8400000000101").reference("zz-polo-T5").description("Zarzuela - Polo T5")
                        .retailPrice(new BigDecimal("18.8")).stock(5).providerEntity(providers[0])
                        .registrationDate(LocalDateTime.now()).discontinued(false).build(),
                ArticleEntity.builder().barcode("8400000000102").reference("zz-polo-T6").description("Zarzuela - Polo T6")
                        .retailPrice(new BigDecimal("19.8")).stock(5).providerEntity(providers[0])
                        .registrationDate(LocalDateTime.now()).discontinued(false).build(),
                ArticleEntity.builder().barcode("8400000000093").description("WARNING!!!. This article can be modificated")
                        .retailPrice(new BigDecimal("17.7")).stock(10).registrationDate(LocalDateTime.now())
                        .discontinued(false).build(),
                ArticleEntity.builder().barcode("8400000000100").reference("without provider").description("without provider")
                        .retailPrice(new BigDecimal("0.12")).stock(5).registrationDate(LocalDateTime.now())
                        .discontinued(false).build(),
        };
        this.articleDao.saveAll(List.of(articles));
        LogManager.getLogger(this.getClass()).warn("        ------- articles");
        ArticlesTreeEntity[] singleList = {
                new SingleArticleEntity(articles[0]),
                new SingleArticleEntity(articles[1]),
                new SingleArticleEntity(articles[2]),
                new SingleArticleEntity(articles[3]),
                new SingleArticleEntity(articles[4]),
                new SingleArticleEntity(articles[5]),
                new SingleArticleEntity(articles[6]),
                new SingleArticleEntity(articles[7]),
                new SingleArticleEntity(articles[8]),
                new SingleArticleEntity(articles[9]),
        };
        this.articlesTreeDao.saveAll(Arrays.asList(singleList));
        ArticlesTreeEntity[] sizeCompositeList = {
                new CompositeArticleEntity("Zz Falda", TreeType.SIZES, "Zarzuela - Falda"),
                new CompositeArticleEntity("Zz Polo", TreeType.SIZES, "Zarzuela - Polo")
        };
        sizeCompositeList[0].add(singleList[0]);
        sizeCompositeList[0].add(singleList[1]);
        sizeCompositeList[1].add(singleList[6]);
        sizeCompositeList[1].add(singleList[7]);
        this.articlesTreeDao.saveAll(Arrays.asList(sizeCompositeList));
        ArticlesTreeEntity[] compositeList = {
                new CompositeArticleEntity("root", TreeType.ARTICLES, "root"),
                new CompositeArticleEntity("Zz", TreeType.ARTICLES, "Zarzuela"),
                new CompositeArticleEntity("varios", TreeType.ARTICLES, "varios"),
        };
        this.articlesTreeDao.saveAll(Arrays.asList(compositeList));
        compositeList[0].add(compositeList[1]);
        compositeList[0].add(compositeList[2]);
        compositeList[0].add(singleList[2]);
        compositeList[1].add(sizeCompositeList[0]);
        compositeList[1].add(sizeCompositeList[1]);
        compositeList[1].add(singleList[3]);
        compositeList[2].add(singleList[4]);
        compositeList[2].add(singleList[5]);
        this.articlesTreeDao.saveAll(Arrays.asList(compositeList));
        LogManager.getLogger(this.getClass()).warn("        ------- articles tree");
        ShoppingEntity[] shoppingList = {
                new ShoppingEntity(articles[0], articles[0].getDescription(), articles[0].getRetailPrice(),
                        1, ZERO, ShoppingState.COMMITTED),
                new ShoppingEntity(articles[1], articles[1].getDescription(), articles[1].getRetailPrice(),
                        3, new BigDecimal("50"), ShoppingState.NOT_COMMITTED),
                new ShoppingEntity(articles[0], articles[0].getDescription(), articles[0].getRetailPrice(),
                        1, BigDecimal.TEN, ShoppingState.COMMITTED),
                new ShoppingEntity(articles[2], articles[2].getDescription(), articles[2].getRetailPrice(),
                        3, new BigDecimal("50"), ShoppingState.COMMITTED),
                new ShoppingEntity(articles[4], articles[4].getDescription(), articles[4].getRetailPrice(),
                        3, ZERO, ShoppingState.COMMITTED),
                new ShoppingEntity(articles[5], articles[5].getDescription(), articles[5].getRetailPrice(),
                        2, new BigDecimal("50"), ShoppingState.COMMITTED),
                new ShoppingEntity(articles[6], articles[6].getDescription(), articles[6].getRetailPrice(),
                        3, ZERO, ShoppingState.COMMITTED),
                new ShoppingEntity(articles[7], articles[7].getDescription(), articles[7].getRetailPrice(),
                        1, new BigDecimal("50"), ShoppingState.COMMITTED),
                new ShoppingEntity(articles[8], articles[8].getDescription(), articles[8].getRetailPrice(),
                        3, ZERO, ShoppingState.COMMITTED),
                new ShoppingEntity(articles[9], articles[9].getDescription(), articles[9].getRetailPrice(),
                        2, ZERO, ShoppingState.COMMITTED),
        };
        LocalDateTime date = LocalDateTime.of(2019, Month.JANUARY, 12, 10, 10);
        TicketEntity[] tickets = {
                new TicketEntity("5fa45e863d6e834d642689ac", "nUs81zZ4R_iuoq0_zCRm6A",
                        List.of(shoppingList[0], shoppingList[1]), date, new BigDecimal("20.0"),
                        ZERO, ZERO, "note", "666666000"),
                new TicketEntity("5fa45f6f3a61083cb241289c", "lpiHOlsoS_WkkEyWeFNJtg",
                        List.of(shoppingList[2]), date, new BigDecimal("25.0"),
                        ZERO, ZERO, "note", "666666004"),
                new TicketEntity("5fa4603b7513a164c99677ac", "FGhfvfMORj6iKmzp5aERAA",
                        List.of(shoppingList[3], shoppingList[4]), date, new BigDecimal("18.0"),
                        ZERO, ZERO, "note", "666666004"),
                new TicketEntity("5fa4608f4928694ef5980e4c", "WB9-e8xQT4ejb74r1vLrCw",
                        List.of(shoppingList[5]), date, new BigDecimal("20"),
                        new BigDecimal("5"), ZERO, "note", "666666005"),


                new TicketEntity("5fa4608f4928694ef5980e4d", "WB9-e8xQT4ejb74r1vLrCa",
                        List.of(shoppingList[1], shoppingList[2],shoppingList[5],shoppingList[6],shoppingList[7],shoppingList[8]), LocalDateTime.of(2019, Month.DECEMBER, 21, 10, 10), new BigDecimal("20"),
                        new BigDecimal("6"), ZERO, "note", "666666006"),
                new TicketEntity("5fa4608f4928694ef5980e4e", "WB9-e8xQT4ejb74r1vLrCb",
                        List.of(shoppingList[3], shoppingList[4],shoppingList[5],shoppingList[6],shoppingList[7],shoppingList[8],shoppingList[9]), LocalDateTime.of(2020, Month.FEBRUARY, 12, 10, 10), new BigDecimal("20"),
                        new BigDecimal("9"), ZERO, "note", "666666007"),
                new TicketEntity("5fa4608f4928694ef5980e4f", "WB9-e8xQT4ejb74r1vLrCc",
                        List.of(shoppingList[3], shoppingList[4],shoppingList[5],shoppingList[8],shoppingList[9]), LocalDateTime.of(2020, Month.APRIL, 15, 10, 10), new BigDecimal("20"),
                        new BigDecimal("6"), ZERO, "note", "666666008"),
                new TicketEntity("5fa4608f4928694ef5980e4g", "WB9-e8xQT4ejb74r1vLrCd",
                        List.of(shoppingList[2], shoppingList[3],shoppingList[5],shoppingList[6],shoppingList[7]), LocalDateTime.of(2020, Month.JANUARY, 22, 10, 10), new BigDecimal("20"),
                        new BigDecimal("5"), ZERO, "note", "666666009"),

                new TicketEntity("5fa4609f4928694ef5980e4d", "WB9-e8xQT4ejb74r1vLrCa",
                        List.of(shoppingList[3], shoppingList[4],shoppingList[5],shoppingList[6],shoppingList[7],shoppingList[8]), LocalDateTime.of(2020, Month.MARCH, 21, 10, 10), new BigDecimal("20"),
                        new BigDecimal("6"), ZERO, "note", "666666006"),
                new TicketEntity("5fa4608f4927684ef5980e4e", "WB9-e8xQT4ejb74r1vLrCb",
                        List.of(shoppingList[3], shoppingList[5],shoppingList[6],shoppingList[7],shoppingList[8],shoppingList[9]), LocalDateTime.of(2020, Month.JUNE, 12, 10, 10), new BigDecimal("20"),
                        new BigDecimal("9"), ZERO, "note", "666666007"),
                new TicketEntity("5fa4638f4928694ef5981e4f", "WB9-e8xQT4ejb74r1vLrCc",
                        List.of(shoppingList[3], shoppingList[4],shoppingList[5],shoppingList[8],shoppingList[9]), LocalDateTime.of(2020, Month.AUGUST, 15, 10, 10), new BigDecimal("20"),
                        new BigDecimal("6"), ZERO, "note", "666666008"),
                new TicketEntity("5Ga4648f4928694ef5987e4g", "WB9-e8xQT4ejb74r1vLrCd",
                        List.of(shoppingList[2], shoppingList[3],shoppingList[5],shoppingList[6],shoppingList[7]), LocalDateTime.of(2020, Month.OCTOBER, 22, 10, 10), new BigDecimal("20"),
                        new BigDecimal("5"), ZERO, "note", "666666009"),
                new TicketEntity("5fa4658f4925694ef5980e4k", "WB9-e8xQT4ejb74r1vLrCc",
                        List.of(shoppingList[3], shoppingList[4],shoppingList[5],shoppingList[8],shoppingList[9]), LocalDateTime.of(2021, Month.JANUARY, 15, 10, 10), new BigDecimal("20"),
                        new BigDecimal("6"), ZERO, "note", "666666008"),
                new TicketEntity("5fa4608f4928794ef5980y4i", "WB9-e8xQT4ejb74r1vLrCd",
                        List.of(shoppingList[2], shoppingList[3],shoppingList[5],shoppingList[6],shoppingList[7]), LocalDateTime.of(2021, Month.MARCH, 22, 10, 10), new BigDecimal("20"),
                        new BigDecimal("5"), ZERO, "note", "666666009"),
        };
        this.ticketDao.saveAll(Arrays.asList(tickets));
        LogManager.getLogger(this.getClass()).warn("        ------- tickets");
        LocalDateTime recentDate = LocalDateTime.of(2022, Month.MARCH, 13, 10, 10);
        LocalDateTime expiredDate = LocalDateTime.of(2022, Month.FEBRUARY, 13, 10, 10);
        ShoppingEntity[] shoppings = {
                new ShoppingEntity(articles[0], articles[0].getDescription(), articles[0].getRetailPrice(),
                        2, BigDecimal.ONE, ShoppingState.NOT_COMMITTED),
                new ShoppingEntity(articles[1], articles[1].getDescription(), articles[1].getRetailPrice(),
                        3, ZERO, ShoppingState.NOT_COMMITTED),
                new ShoppingEntity(articles[0], articles[0].getDescription(), articles[0].getRetailPrice(),
                        1, BigDecimal.TEN, ShoppingState.NOT_COMMITTED),
                new ShoppingEntity(articles[2], articles[2].getDescription(), articles[2].getRetailPrice(),
                        3, new BigDecimal("30"), ShoppingState.NOT_COMMITTED),
        };
        BudgetEntity[] budgets = {
                new BudgetEntity("5fa45e863d6e834d642689ac", "nUs81zZ4R_iuoq0_zCRm6A", recentDate,
                        List.of(shoppings[0], shoppings[1])),
                new BudgetEntity("5fa45f6f3a61083cb241289c", "lpiHOlsoS_WkkEyWeFNJtg", expiredDate,
                        List.of(shoppings[2])),
                new BudgetEntity("5fa45f6f3a61083cb241220c", "lpiHOlsoS_WkkEyWeFNJtw", recentDate,
                        List.of(shoppings[3], shoppings[1]))
        };
        this.budgetDao.saveAll(Arrays.asList(budgets));
        LogManager.getLogger(this.getClass()).warn("        ------- budgets");
        CashierEntity[] cashiers = {
                CashierEntity.builder().id("chasierId1").initialCash(new BigDecimal("1000.0"))
                        .cashSales(new BigDecimal("200.0")).cardSales(new BigDecimal("300.0"))
                        .usedVouchers(new BigDecimal("10.0")).deposit(new BigDecimal("500.0"))
                        .withdrawal(new BigDecimal("100.0")).lostCash(new BigDecimal("100.0"))
                        .lostCard(ZERO).finalCash(new BigDecimal("1400.0")).comment("cashierClosure1")
                        .closureDate(LocalDateTime.now().minusYears(1)).openingDate(LocalDateTime.now().minusYears(1))
                        .build(),
                CashierEntity.builder().id("chasierId2").initialCash(new BigDecimal("2000.0"))
                        .cashSales(new BigDecimal("400.0")).cardSales(new BigDecimal("600.0"))
                        .usedVouchers(new BigDecimal("20.0")).deposit(new BigDecimal("1000.0"))
                        .withdrawal(new BigDecimal("200.0")).lostCash(ZERO).lostCard(new BigDecimal("200.0"))
                        .finalCash(new BigDecimal("2800.0")).comment("cashierClosure2")
                        .closureDate(LocalDateTime.now().minusMonths(1)).openingDate(LocalDateTime.now().minusMonths(1))
                        .build(),
                CashierEntity.builder().id("chasierId3").initialCash(new BigDecimal("1500.0"))
                        .cashSales(new BigDecimal("300.0")).cardSales(new BigDecimal("450.0"))
                        .usedVouchers(new BigDecimal("15.0")).deposit(new BigDecimal("750.0"))
                        .withdrawal(new BigDecimal("150.0")).lostCash(new BigDecimal("50.0"))
                        .lostCard(new BigDecimal("100.0")).finalCash(new BigDecimal("2100.0"))
                        .comment("cashierClosure3").closureDate(LocalDateTime.now().minusYears(1).minusMonths(1))
                        .openingDate(LocalDateTime.now().minusYears(1).minusMonths(1)).build(),
        };
        this.cashierDao.saveAll(Arrays.asList(cashiers));
        LogManager.getLogger(this.getClass()).warn("        ------- cashiers");

        OrderEntity[] orders = {
                new OrderEntity(1, "Order1"),
                new OrderEntity(2, "Order2"),
                new OrderEntity(3, "Order3"),
                new OrderEntity(4, "Order4"),
                new OrderEntity(5, "Order5"),
                new OrderEntity(6, "Order6"),
                new OrderEntity(7, "Order7"),
        };
        this.orderDao.saveAll(List.of(orders));
        LogManager.getLogger(this.getClass()).warn("         ------- orders");
        ProviderInvoiceEntity[] providerInvoices = {
                ProviderInvoiceEntity.builder().id("identity1").providerEntity(providers[0])
                        .orderReference(orders[0].getOrderReference()).baseTax(new BigDecimal(1111))
                        .textValue(new BigDecimal(11)).creationDate(LocalDateTime.now()).build(),
                ProviderInvoiceEntity.builder().id("identity2").providerEntity(providers[1])
                        .orderReference(orders[1].getOrderReference()).baseTax(new BigDecimal(2222))
                        .textValue(new BigDecimal(22)).creationDate(LocalDateTime.now()).build(),
                ProviderInvoiceEntity.builder().id("identity3").providerEntity(providers[2])
                        .orderReference(orders[2].getOrderReference()).baseTax(new BigDecimal(3333))
                        .textValue(new BigDecimal(33)).creationDate(LocalDateTime.now()).build(),
                ProviderInvoiceEntity.builder().id("identity4").providerEntity(providers[3])
                        .orderReference(orders[3].getOrderReference()).baseTax(new BigDecimal(4444))
                        .textValue(new BigDecimal(44))
                        .creationDate(LocalDateTime.of(2021, 1, 12, 14, 20)).build(),
                ProviderInvoiceEntity.builder().id("identity5").providerEntity(providers[3])
                        .orderReference(orders[4].getOrderReference()).baseTax(new BigDecimal(5555))
                        .textValue(new BigDecimal(55))
                        .creationDate(LocalDateTime.of(2021, 2, 5, 17, 30)).build(),
                ProviderInvoiceEntity.builder().id("identity6").providerEntity(providers[3])
                        .orderReference(orders[5].getOrderReference()).baseTax(new BigDecimal(6666))
                        .textValue(new BigDecimal(66))
                        .creationDate(LocalDateTime.of(2021, 7, 10, 19, 25)).build(),
                ProviderInvoiceEntity.builder().id("identity7").providerEntity(providers[3])
                        .orderReference(orders[6].getOrderReference()).baseTax(new BigDecimal(7777))
                        .textValue(new BigDecimal(77))
                        .creationDate(LocalDateTime.of(2021, 8, 5, 20, 50)).build(),
        };
        this.providerInvoiceDao.saveAll(List.of(providerInvoices));
        LogManager.getLogger(this.getClass()).warn("         ------- providerInvoices");

        TechnicalSupportAnswerEntity[] technicalSupportAnswerEntities1 = {
                TechnicalSupportAnswerEntity.builder().answer("We will check and come back to you soon.").dateSent(LocalDateTime.now()).build(),
                TechnicalSupportAnswerEntity.builder().answer("Working on it.").dateSent(LocalDateTime.now()).build(),
                TechnicalSupportAnswerEntity.builder().answer("Fixed, please check and if it is not fixed open new request. Thank you for your patience.").dateSent(LocalDateTime.now()).build(),
        };
        TechnicalSupportAnswerEntity[] technicalSupportAnswerEntities2 = {
                TechnicalSupportAnswerEntity.builder().answer("You can change it by yourself from the Profile option on the top menu.").dateSent(LocalDateTime.now()).build(),
                TechnicalSupportAnswerEntity.builder().answer("Also we will check if we can change it and send you an email, please wait.").dateSent(LocalDateTime.now()).build(),
        };
        TechnicalSupportAnswerEntity[] technicalSupportAnswerEntities3 = {
                TechnicalSupportAnswerEntity.builder().answer("We are updating the payment options, please be patient, we will come back to you with a solution.").dateSent(LocalDateTime.now()).build(),
        };
        LogManager.getLogger(this.getClass()).warn("        ------- technical support answers");

        TechnicalSupportRequestEntity[] technicalSupportRequestEntities = {
                TechnicalSupportRequestEntity.builder().request("I can not see my articles.").answers(List.of(technicalSupportAnswerEntities1)).resolved(true).identifier("1").build(),
                TechnicalSupportRequestEntity.builder().request("I want to change my password but I do not know how.").answers(List.of(technicalSupportAnswerEntities2)).resolved(false).identifier("2").build(),
                TechnicalSupportRequestEntity.builder().request("When opens the shop of UMP?").resolved(true).identifier("3").build(),
                TechnicalSupportRequestEntity.builder().request("Articles loads very slow, please fix it.").resolved(false).identifier("4").build(),
                TechnicalSupportRequestEntity.builder().request("My payment card is not being recognized.").answers(List.of(technicalSupportAnswerEntities3)).resolved(false).identifier("5").build(),
        };
        this.technicalSupportDao.saveAll(List.of(technicalSupportRequestEntities));
        LogManager.getLogger(this.getClass()).warn("        ------- technical support requests");

        InvoiceEntity[] invoiceEntities = {
                InvoiceEntity.builder()
                        .identity(20221).creationDate(LocalDateTime.now())
                        .userMobile("666666004").ticketId("5fa45f6f3a61083cb241289c")
                        .baseTax(new BigDecimal(90)).taxValue(new BigDecimal(10))
                        .build()
        };
        this.invoiceDao.saveAll(List.of(invoiceEntities));
        LogManager.getLogger(this.getClass()).warn("         ------- Invoices");

        CreditLineEntity[] creditLineEntities = {
                CreditLineEntity.builder()
                        .mobile("666666000")
                        .creditSales(new ArrayList<>())
                        .registrationDateTime(LocalDateTime.now())
                        .build()
        };
        this.creditLineDao.saveAll(List.of(creditLineEntities));
        LogManager.getLogger(this.getClass()).warn("         ------- Credit lines");

        LogManager.getLogger(this.getClass()).warn("------- Initial Load from JAVA -----------");
        CustomerDiscountEntity[] customerDiscountEntities = {
                CustomerDiscountEntity.builder()
                        .userMobile("010101")
                        .username("Ana")
                        .discount(5)
                        .minimmumPurchase(100)
                        .registrationDate(LocalDate.of(2020, 10, 10))
                        .build(),

                CustomerDiscountEntity.builder()
                        .userMobile("333344")
                        .discount(15)
                        .registrationDate(LocalDate.of(2021, 12, 10))
                        .minimmumPurchase(120)
                        .note("nota")
                        .build(),
                CustomerDiscountEntity.builder()
                        .userMobile("00000000")
                        .username("Pedro")
                        .registrationDate(LocalDate.of(2020, 2, 20))
                        .discount(5)
                        .minimmumPurchase(45)
                        .note("nota")
                        .build(),
                CustomerDiscountEntity.builder()
                        .userMobile("222")
                        .username("Sara")
                        .discount(15)
                        .minimmumPurchase(400)
                        .note("Esto es una nota")
                        .build(),
                CustomerDiscountEntity.builder()
                        .userMobile("111")
                        .username("Luis")
                        .registrationDate(LocalDate.of(2019, 5, 10))
                        .discount(25)
                        .minimmumPurchase(300)
                        .note("Nota de Luis")
                        .build(),
        };
        this.customerDiscountDao.saveAll(List.of(customerDiscountEntities));
        LogManager.getLogger(this.getClass()).warn("        ------- customerDiscounts");

        ShoppingEntity[] entities = {
                new ShoppingEntity(articles[0], null, articles[0].getRetailPrice(), 1, ZERO, ShoppingState.NOT_COMMITTED),
                new ShoppingEntity(articles[2], null, articles[2].getRetailPrice(), 2, ZERO, ShoppingState.NOT_COMMITTED)
        };
        ShoppingCartEntity[] shoppingCartEntities = {
                ShoppingCartEntity.builder()
                        .shoppingEntities(List.of(entities))
                        .updateDate(LocalDateTime.now())
                        .mobile("6")
                        .build()
        };
        this.cartDao.saveAll(List.of(shoppingCartEntities));
        LogManager.getLogger(this.getClass()).warn("        ------- shoppingCarts");


        ArticleLossEntity[] articleLossEntities = {
                ArticleLossEntity.builder().barcode("1").amount(5).build(),
                ArticleLossEntity.builder().barcode("2").amount(5).build(),
                ArticleLossEntity.builder().barcode("8400000000086").amount(5).build(),
                ArticleLossEntity.builder().barcode("8400000000093").amount(5).build()
                , ArticleLossEntity.builder().barcode("8400000000100").amount(5).build()
        };
        this.articleLossDao.saveAll(List.of(articleLossEntities));
        LogManager.getLogger(this.getClass()).warn("        ------- articleLossEntities");


        StockAuditEntity[] stockAuditEntities = {
                StockAuditEntity.builder().id("id1")
                        .articlesWithoutAudit(List.of(articles[0], articles[1], articles[2], articles[3]))
                        .creationDate(LocalDateTime.of(2020, 10, 5, 5, 2))
                        .closeDate(LocalDateTime.of(2021, 1, 1, 5, 2))
                        .articleLoss(List.of(articleLossEntities[0], articleLossEntities[1]))
                        .lossValue(new BigDecimal("27.8"))
                        .build(),
                StockAuditEntity.builder().id("id2")
                        .articlesWithoutAudit(List.of(articles[0], articles[1], articles[2], articles[3]))
                        .creationDate(LocalDateTime.of(2020, 10, 5, 5, 2))
                        .closeDate(null)
                        .articleLoss(List.of(articleLossEntities[3]))
                        .build(),
        };
        this.stockAuditDao.saveAll(List.of(stockAuditEntities));
        LogManager.getLogger(this.getClass()).warn("        ------- stockAuditEntities");

    }


}



*/