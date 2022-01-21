package com.nttdata.bootcamp.microservicio2.business.impl;

import com.nttdata.bootcamp.microservicio2.business.AccountService;
import com.nttdata.bootcamp.microservicio2.expose.AccountController;
import com.nttdata.bootcamp.microservicio2.model.Account;
import com.nttdata.bootcamp.microservicio2.model.AccountType;
import com.nttdata.bootcamp.microservicio2.model.Customer;
import com.nttdata.bootcamp.microservicio2.model.CustomerType;
import com.nttdata.bootcamp.microservicio2.model.Dto.AccountDto;
import com.nttdata.bootcamp.microservicio2.model.Dto.CustomerDto;
import com.nttdata.bootcamp.microservicio2.repository.AccountRepository;
import com.nttdata.bootcamp.microservicio2.utils.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;
import java.util.NoSuchElementException;
import java.util.Random;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * [Description]. <br/>
 * <b>Class</b>: {@link AccountServiceImpl}<br/>
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

@Slf4j
@Service
public class AccountServiceImpl implements AccountService{

  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private WebClient webClientUser;

  public Mono<Account>create(Account account) {
	 
	try {
		
		if( !account.getCustomer().getId().isBlank() && account.getCustomer().getCustomerType().getDescription().equals("personal")) {
			
			if(findByCustomerId(account.getCustomer().getId()) == null ) {
				account.setCustomer(findByIdCustomerService(account.getCustomer().getId()).block());
				Random accountNumberRandom = new Random();
				
				account.setAccountNumber(Long.toString(accountNumberRandom.nextLong()));
				account.setCurrency("Soles");
				account.setStatus("true");
				account.setAmountAvailable(0.0);
				
				return accountRepository.insert(account);
			}else {
				log.info("El cliente ya tiene cuenta creada");
				return null;
			}
			
        } else if (!account.getCustomer().getId().isBlank() && account.getCustomer().getCustomerType().getDescription().equals("empresarial")){
            log.warn("No se creó la cuenta");
            if(findByCustomerId(account.getCustomer().getId()) == null ) {
				account.setCustomer(findByIdCustomerService(account.getCustomer().getId()).block());
				Random accountNumberRandom = new Random();
				
				account.setAccountNumber(Long.toString(accountNumberRandom.nextLong()));
				account.setCurrency("Soles");
				account.setStatus("true");
				account.getAccountType().setCodigo("002");
				account.setAmountAvailable(0.0);
				
				return accountRepository.insert(account);
            }
        }
		
	}catch(Exception e) {
		System.out.println("Something went wrong.");
	}
	return accountRepository.insert(account);
	
  }
  
  private Mono<Customer> accountToCreateValidation(AccountDto accountForCreate, Customer customerExtracted) {
      log.info("Existe un cliente");
      
      //CustomerType:   personal = '1' y empresarial = '2'
      if (customerExtracted.getCustomerType().getCodigo().equals("1")){
          return findByCustomerId(customerExtracted.getId())
                  .filter(retrievedAccount -> retrievedAccount.getStatus().equals("true"))
                  .hasElements()
                  .flatMap(haveAnAccount -> {
                      if (haveAnAccount) {
                          return Mono.error(new UserNotFoundException("Cliente ya tiene una cuenta"));
                      }
                      else {
                          log.info("Account successfully validated");
                          return Mono.just(customerExtracted);
                      }
                  });
      } else {
          log.info("Credit successfully validated");
          return Mono.just(customerExtracted);
      }
  }

  @Override
  public Mono<Customer> findByIdCustomerService(String id) {
      log.info("Obteniendo cliente con id:", id);
      return webClientUser.get()
    		  .uri(uriBuilder -> uriBuilder
                      .path("v1/customers/"+ id)
                      .build())
              .retrieve()
              .bodyToMono(Customer.class);
     
  }

@Override
  public Mono<Account> findById(String accountId) {
    return accountRepository.findById(accountId);
  }

  @Override
  public Flux<Account> findAll() {
    return accountRepository.findAll();
  }

  @Override
  public Mono<Account> update(Account account) {
    return accountRepository.save(account);
  }

  @Override
  public Mono<Account> change(Account account) {
    return accountRepository.findById(account.getId())
        .flatMap(accountDB -> {
          return create(account);
        })
        .switchIfEmpty(Mono.empty());
  }

  @Override
  public Mono<Account> remove(String accountId) {
    return accountRepository
        .findById(accountId)
        .flatMap(p -> accountRepository.deleteById(p.getId()).thenReturn(p));

  }



@Override
public Flux<Account> findByCustomerId(String id) {
    log.info("End of operation to retrieve accounts of the customer with id: [{}]", id);
    return accountRepository.findAccountsByCustomerId(id);
    //findAccountsByCustomerId
}

@Override
public Mono<Account> findAccountByCustomerId(Customer customerId) {
	
	return accountRepository.findAccountByCustomerId(customerId);
}



  
}
