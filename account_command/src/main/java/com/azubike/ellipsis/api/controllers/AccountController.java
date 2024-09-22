package com.azubike.ellipsis.api.controllers;

import com.azubike.ellipsis.api.commands.DepositFundsCommand;
import com.azubike.ellipsis.api.commands.OpenAccountCommand;
import com.azubike.ellipsis.api.dto.OpenAccountResponse;
import com.azubike.ellipsis.dto.BaseResponse;
import com.azubike.ellipsis.exceptions.AggregateNotFoundException;
import com.azubike.ellipsis.infrastructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/accounts")
@Slf4j
public class AccountController {
    private final CommandDispatcher commandDispatcher;


    @PostMapping("/open-account")
    ResponseEntity<BaseResponse> openAccount(@RequestBody OpenAccountCommand command) {
        var id = UUID.randomUUID().toString();
        command.setId(id);
        try {
            // this sends the request to the CommandHandler for the OpenAccountCommand
            // which creates a new Aggregate Object , raises an OPEN_ACCOUNT_EVENT
            // mutates the aggregate state
            // then saves the aggregate object in the EventSource DB
            // Sends a message to the topic for the queryService to consume
            commandDispatcher.send(command);
            return ResponseEntity.status(HttpStatus.CREATED).body(OpenAccountResponse.builder().id(id)
                    .message("Account successfully created")
                    .build());

        } catch (IllegalStateException ex) {
            log.warn(" Open Account Client response error {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.builder().message(ex.getMessage()).build());
        } catch (Exception ex) {
            log.error("Something wrong happened while Opening Account {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseResponse.builder().message(ex.getMessage()).build());
        }
    }


    @PutMapping("/deposit-funds/{id}")
    ResponseEntity<BaseResponse> depositFunds(@RequestBody DepositFundsCommand command , @PathVariable("id") String id) {
         try {
               command.setId(id);
               commandDispatcher.send(command);
             return ResponseEntity.status(HttpStatus.OK).body( BaseResponse.builder()
                     .message(String.format("Account %s successfully funded with amount %s", id, command.getAmount()))
                     .build());

         }catch(IllegalStateException | AggregateNotFoundException ex){
              log.warn("DepositFunds Client response error {}", ex.getMessage());
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.builder().message(ex.getMessage()).build());
         }catch(Exception ex){
             log.error("Something wrong happened while Depositing Funds {}", ex.getMessage());
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseResponse.builder().message(ex.getMessage()).build());
         }
    }
}
