package com.azubike.ellipsis.infrastructure.handlers;

import com.azubike.ellipsis.domain.AccountRepository;
import com.azubike.ellipsis.domain.BankAccountEntity;
import com.azubike.ellipsis.events.AccountClosedEvent;
import com.azubike.ellipsis.events.AccountOpenedEvent;
import com.azubike.ellipsis.events.FundsDepositedEvent;
import com.azubike.ellipsis.events.FundsWithdrawnEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.BiConsumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountEventsHandler implements EventHandler {

    private final AccountRepository accountRepository;

    @Override
    public void on(AccountOpenedEvent event) {
        BankAccountEntity accountEntity = BankAccountEntity.builder().accountHolder(event.getAccountHolder())
                .accountType(event.getAccountType())
                .id(event.getId())
                .creationDate(event.getCreatedDate())
                .balance(event.getOpeningBalance())
                .build();
        BankAccountEntity save = accountRepository.save(accountEntity);
        log.info("Saved account {}", save.getAccountHolder());

    }

    @Override
    public void on(FundsDepositedEvent event) {
        processEvent(event.getId(), event.getAmount(), (account, amount) -> account.setBalance(account.getBalance() + amount));
    }

    @Override
    public void on(FundsWithdrawnEvent event) {
        processEvent(event.getId(), event.getAmount(), (account, amount) -> account.setBalance(account.getBalance() - amount));
    }


    @Override
    public void on(AccountClosedEvent event) {
        Optional<BankAccountEntity> optAccount = accountRepository.findById(event.getId());
        optAccount.ifPresent(bankAccountEntity -> {
            accountRepository.deleteById(bankAccountEntity.getId());
            log.info("Deleted account {}", bankAccountEntity.getId());
        });

    }


    private void processEvent(String accountId, double amount, BiConsumer<BankAccountEntity, Double> balanceUpdater) {
        Optional<BankAccountEntity> optAccount = accountRepository.findById(accountId);

        if (optAccount.isEmpty()) {
            log.warn("Account {} not found", accountId);
        } else {
            BankAccountEntity bankAccountEntity = optAccount.get();
            log.info("Previous Account balance for {} is {}", bankAccountEntity.getAccountHolder(), bankAccountEntity.getBalance());

            balanceUpdater.accept(bankAccountEntity, amount);
            accountRepository.save(bankAccountEntity);

            log.info("New Account balance for {} is {}", bankAccountEntity.getAccountHolder(), bankAccountEntity.getBalance());
        }
    }
}
