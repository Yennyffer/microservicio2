package com.nttdata.bootcamp.microservicio2.repository;

import com.nttdata.bootcamp.microservicio2.model.Account;
import com.nttdata.bootcamp.microservicio2.model.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * [Description]. <br/>
 * <b>Class</b>: {@link AccountRepository}<br/>
 * <b>Copyright</b>: &Copy; 2022 NTT DATA SAC. <br/>
 * <b>Company</b>: NTT DATA SAC. <br/>
 *
 * @author Yennyffer Lizana <br/>
 * <u>Developed by</u>: <br/>
 * <ul>
 * <li>{USERNAME}. (acronym) From (EVE)</li>
 * </ul>
 * <u>Changes</u>:<br/>
 * <ul>
 * <li>ene. 08, 2022 (acronym) Creation class.</li>
 * </ul>
 * @version 1.0
 */

@Repository
public interface AccountRepository 
    extends ReactiveMongoRepository<Account,String> {
	
    Flux<Account>  findAccountsByCustomerId(String id);
    
    Mono<Account> findAccountByCustomerId(Customer customer);
    
}
