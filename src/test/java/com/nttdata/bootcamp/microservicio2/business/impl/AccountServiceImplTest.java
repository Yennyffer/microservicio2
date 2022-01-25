package com.nttdata.bootcamp.microservicio2.business.impl;

import com.nttdata.bootcamp.microservicio2.business.AccountService;
import com.nttdata.bootcamp.microservicio2.model.Account;
import com.nttdata.bootcamp.microservicio2.model.AccountType;
import com.nttdata.bootcamp.microservicio2.model.Customer;
import com.nttdata.bootcamp.microservicio2.repository.AccountRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceImplTest {
    @Autowired
    private AccountService accountService;
    @MockBean
    private AccountRepository accountRepository;

    private static final Customer customer = new Customer();
    private static final Account mockAccount = new Account();
    private static final Account mockAccountRemove = new Account();
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
    void create() {
        Mockito.when(accountRepository.save(mockAccount)).thenReturn(Mono.just(mockAccount));
    }

    @Test
    void findByIdCustomerService() {
    }

    @Test
    void findById() {
        Mockito.when(accountRepository.findById(id)).thenReturn(Mono.just(mockAccount));
    }

    @Test
    void findAll() {
        Mockito.when(accountRepository.findAll()).thenReturn(Flux.fromIterable(accountListMock));
        Flux<Account> account = accountService.findAll();
    }

    @Test
    void update() {
        Mockito.when(accountRepository.findById(id)).thenReturn(Mono.just(mockAccount));
        Mockito.when(accountRepository.save(mockAccount)).thenReturn(Mono.just(mockAccount));
    }

    @Test
    void remove() {
        Mockito.when(accountRepository.findById(id)).thenReturn(Mono.just(mockAccountRemove));
        Mockito.when(accountRepository.save(mockAccountRemove)).thenReturn(Mono.just(mockAccountRemove));
        Mono<Account> account = accountService.remove(id);
    }
}