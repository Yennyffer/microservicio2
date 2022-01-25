package com.nttdata.bootcamp.microservicio2.expose;

import com.nttdata.bootcamp.microservicio2.business.AccountService;
import com.nttdata.bootcamp.microservicio2.model.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Address;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "20000")
class AccountControllerTest {
    @MockBean
    private AccountService accountService;
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private AccountController accountController;

    private static final Customer customer = new Customer();
    private static final Account mockAccount = new Account();
    private static final AccountType accountType = new AccountType();
    private static final List<Account> accountListMock = new ArrayList<>();

    private static final String id = "a5d41fd1";
    private static final String accountNumber = "421375896253";
    private static final String currency = "Soles";
    private static final Double amountAvailable = 0.0;
    private static final String status = "ACTIVO";
    private static final Double maintenanceCommission = 3.2;
    private static final int amountMonthlyMovements = 3;
    private static final String accountTypeCodigo = "001";
    private static final String accountTypeDescription = "Ahorro";
    private final static String customerId = "61d874f0dsf";
    private final static String customerFirstName = "Jose Luis";
    private final static String customerLastName = "Peralta";
    private final static String customerEmail = "joseluis@gmail.com";
    private final static String customerNumberDocumentIdentity = "72159854";

    @BeforeAll
    static void beforeAll() {
        mockAccount.setId(id);
        mockAccount.setAccountNumber(accountNumber);
        mockAccount.setCurrency(currency);
        mockAccount.setAmountAvailable(amountAvailable);
        mockAccount.setMaintenanceCommission(maintenanceCommission);
        mockAccount.setAmountMonthlyMovements(amountMonthlyMovements);
        mockAccount.setStatus(status);
        accountType.setCodigo(accountTypeCodigo);
        accountType.setDescription(accountTypeDescription);
        mockAccount.setAccountType(accountType);
        customer.setId(customerId);
        customer.setFirstname(customerFirstName);
        customer.setLastname(customerLastName);
        customer.setEmail(customerEmail);
        customer.setNumberDocumentIdentity(customerNumberDocumentIdentity);
        mockAccount.setCustomer(customer);
        accountListMock.add(mockAccount);
    }

    @Test
    void findByIdCustomerService() {
    }

    @Test
    void byId() {
        log.info("--Metodo GET: Obtener una cuenta por ID--");
        Mockito.when(accountService.findById(id)).thenReturn(Mono.just(mockAccount));

        webTestClient.get().uri("/api/v1/accounts/" + id)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void findAll() {
        log.info("--Metodo GET: Obtener todos las cuentas registradas--");
        Mockito.when(accountService.findAll()).thenReturn(Flux.fromIterable(accountListMock));

        webTestClient.get().uri("/api/v1/accounts/all")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void create() {
        log.info("--Metodo POST: Agregar una nueva cuenta--");
        Mockito.when(accountService.create(mockAccount)).thenReturn(Mono.just(mockAccount));
    }

    @Test
    void update() {
        log.info("--Metodo UPDATE: Actualizar una cuenta--");
        Mockito.when(accountService.update(mockAccount)).thenReturn(Mono.just(mockAccount));
    }

    @Test
    void delete() {
        log.info("--Metodo DELETE: Eliminar una cuenta por ID--");
        Mockito.when(accountService.remove(id)).thenReturn(Mono.just(mockAccount));

        webTestClient.delete().uri("/api/v1/accounts/" + id)
                .exchange()
                .expectStatus().isOk();
    }
}