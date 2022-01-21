package com.nttdata.bootcamp.microservicio2.business;

import com.nttdata.bootcamp.microservicio2.model.Account;
import com.nttdata.bootcamp.microservicio2.model.AccountType;
import com.nttdata.bootcamp.microservicio2.model.Customer;
import com.nttdata.bootcamp.microservicio2.model.Dto.CustomerDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * [Description]. <br/>
 * <b>Class</b>: {@link AccountService}<br/>
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

public interface AccountService {

    Mono<Account> create(Account account);
  
    Mono<Account> findById(String accountId);
    
    Mono<Account> findAccountByCustomerId(Customer customerId);
  
    Flux<Account> findAll();
  
    Mono<Account> update(Account account);
  
    Mono<Account> change(Account account);
  
    Mono<Account> remove(String accountId);
    
    Mono<Customer> findByIdCustomerService(String customerId);
    
    Flux<Account> findByCustomerId(String id);
    
    
    
  }
  


