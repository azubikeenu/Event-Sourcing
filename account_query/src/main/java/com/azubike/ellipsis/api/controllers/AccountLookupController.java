package com.azubike.ellipsis.api.controllers;

import com.azubike.ellipsis.api.dto.AccountLookupResponse;
import com.azubike.ellipsis.api.queries.FindAccountByHolderQuery;
import com.azubike.ellipsis.api.queries.FindAccountByIdQuery;
import com.azubike.ellipsis.api.queries.FindAllAccountQuery;
import com.azubike.ellipsis.domain.BankAccountEntity;
import com.azubike.ellipsis.infrastructure.AccountQueryDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
@Slf4j
public class AccountLookupController {
    private final AccountQueryDispatcher dispatcher;

    @GetMapping
    ResponseEntity<AccountLookupResponse> getAllAccounts() {
        try {
            List<BankAccountEntity> bankAccountEntities = dispatcher.send(new FindAllAccountQuery());
            log.info("Retrieving accounts of size {}", bankAccountEntities.size());

            if (bankAccountEntities.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return ResponseEntity.status(HttpStatus.OK).body(AccountLookupResponse.builder().message("Accounts successfully retrieved")
                    .bankAccounts(bankAccountEntities).build());

        } catch (RuntimeException ex) {
            log.error("getAllAccounts Client exception : {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(AccountLookupResponse.builder().message(ex.getMessage()).build());

        } catch (Exception ex) {
            log.error("getAllAccounts Exception : {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(AccountLookupResponse.builder().message(ex.getMessage()).build());
        }

    }


    @GetMapping("/{id}")
    ResponseEntity<AccountLookupResponse> findAccountById(@PathVariable("id") String id ) {
        try {
            List<BankAccountEntity> bankAccountEntities = dispatcher.send(FindAccountByIdQuery.builder().id(id).build());
               return ResponseEntity.status(HttpStatus.OK).body(AccountLookupResponse.builder().message("Account successfully retrieved")
                    .bankAccounts(bankAccountEntities).build());

        } catch (RuntimeException ex) {
            log.error("findAccountById Client exception : {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(AccountLookupResponse.builder()
                    .message(ex.getMessage()).build());

        } catch (Exception ex) {
            log.error("findAccountById Exception : {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(AccountLookupResponse
                    .builder().message(ex.getMessage()).build());
        }

    }



    @GetMapping("holder/{accountHolder}")
    ResponseEntity<AccountLookupResponse> findByAccountHolder( @PathVariable("accountHolder") String accountHolder ) {
        try {

            List<BankAccountEntity> bankAccountEntities = dispatcher.send(FindAccountByHolderQuery.builder().accountHolder(accountHolder).build());
            return ResponseEntity.status(HttpStatus.OK).body(AccountLookupResponse.builder().message("Account successfully retrieved")
                    .bankAccounts(bankAccountEntities).build());

        } catch (RuntimeException ex) {
            log.error("findByAccountHolder Client exception : {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(AccountLookupResponse.builder()
                    .message(ex.getMessage()).build());

        } catch (Exception ex) {
            log.error("findByAccountHolder Exception : {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(AccountLookupResponse
                    .builder().message(ex.getMessage()).build());
        }

    }





}
