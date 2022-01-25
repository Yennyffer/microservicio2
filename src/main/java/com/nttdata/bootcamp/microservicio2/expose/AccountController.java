package com.nttdata.bootcamp.microservicio2.expose;

import com.nttdata.bootcamp.microservicio2.model.Account;
import com.nttdata.bootcamp.microservicio2.model.Customer;
import com.nttdata.bootcamp.microservicio2.model.Dto.CustomerDto;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import com.nttdata.bootcamp.microservicio2.business.AccountService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * [Description]. <br/>
 * <b>Class</b>: {@link AccountController}<br/>
 * <b>Copyright</b>: &Copy; 2022 NTT DATA SAC. <br/>
 * <b>Company</b>: NTT DATA SAC. <br/>
 *
 * @author Yennyffer Lizana <br/>
 * <u>Developed by</u>: <br/>
 * <ul>
 * <li>{USERNAME}. (acronym) From (YEN)</li>
 * </ul>
 * <u>Changes</u>:<br/>
 * <ul>
 * <li>ene. 08, 2022 (acronym) Creation class.</li>
 * </ul>
 * @version 1.0
 */


@RestController
@Slf4j
public class AccountController {

  @Autowired
  private AccountService accountService;
  private static final String ACCOUNT_DELETE =  "DeleteAccount";
  private static final String ACCOUNT_UPDATE =  "UpdateAccount";


  @GetMapping("api/v1/customers/{id}")
  public Mono<Customer> findByIdCustomerService(@PathVariable("id") String customerId) {
    log.info("Obtengo customer by id:", customerId);
    return accountService.findByIdCustomerService(customerId);
  }
  
  
  @GetMapping("/api/v1/accounts/{id}")
  public Mono<Account> byId(@PathVariable("id") String id) {
    log.info("byId>>>>>");
    return accountService.findById(id);
  }
  
  @GetMapping("/api/v1/accounts/all")
  public Flux<Account> findAll() {
    log.info("findAll>>>>>");

    return accountService.findAll();
  }

  @PostMapping("/api/v1/account/")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Account> create(@RequestBody Account account) {
    log.info("create>>>>>");
    return accountService.create(account);
  }

  @CircuitBreaker(name=ACCOUNT_UPDATE, fallbackMethod="fallbackUpdateAccount")
  @PutMapping("/api/v1/account/")
  public Mono<ResponseEntity<Account>> update(@RequestBody Account account) {
    log.info("update>>>>>");
    return accountService.update(account)
        .flatMap(accountUpdate -> Mono.just(ResponseEntity.ok(accountUpdate)))
        .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
  }

  @PatchMapping("/api/v1/accounts")
  public Mono<ResponseEntity<Account>> change(@RequestBody Account account) {
    log.info("change>>>>>");
    return accountService.change(account)
        .flatMap(accountUpdate -> Mono.just(ResponseEntity.ok(accountUpdate)))
        .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
  }

  @CircuitBreaker(name=ACCOUNT_DELETE, fallbackMethod="fallbackDeleteAccount")
  @DeleteMapping("/api/v1/accounts/{id}")
  public Mono<ResponseEntity<Account>> delete(@PathVariable("id") String id) {
    log.info("delete>>>>>");
    return accountService.remove(id)
        .flatMap(account -> Mono.just(ResponseEntity.ok(account)))
        .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
  }
  
  
  public Mono<ResponseEntity<String>> fallbackUpdateAccount(@RequestBody Account account,RuntimeException ex) {
	  return Mono.just(ResponseEntity.ok().body("Actualizando cuenta: "+ account.getAccountNumber()+ "Servicio no disponible."));	  
}

  public Mono<ResponseEntity<String>> fallbackDeleteAccount(@PathVariable("id") String id, RuntimeException ex) {
    return Mono.just(ResponseEntity.ok().body("Se buscó el id: " + id + " Servicio no disponible."));
  }
  
}

