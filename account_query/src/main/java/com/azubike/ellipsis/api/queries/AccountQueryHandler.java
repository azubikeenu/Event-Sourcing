package com.azubike.ellipsis.api.queries;

import com.azubike.ellipsis.api.enums.EqualityType;
import com.azubike.ellipsis.domain.AccountRepository;
import com.azubike.ellipsis.domain.BankAccountEntity;
import com.azubike.ellipsis.domain.BaseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
 import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountQueryHandler implements QueryHandler {
    private final AccountRepository accountRepository;

    @Override
    public List<BaseEntity> handle(FindAllAccountQuery query) {
        List<BankAccountEntity> bankAccountEntities   = accountRepository.findAll();
        return new ArrayList<>(bankAccountEntities);
    }

    @Override
    public List<BaseEntity> handle(FindAccountByHolderQuery query) {
        BankAccountEntity bankAccountEntity = accountRepository.findByAccountHolder(query.getAccountHolder()).orElseThrow(() -> new RuntimeException(
                String.format("Account holder %s not found", query.getAccountHolder())
        ));
        return List.of(bankAccountEntity);
    }

    @Override
    public List<BaseEntity> handle(FindAccountByIdQuery query) {
        BankAccountEntity bankAccountEntity = accountRepository.findById(query.getId()).orElseThrow(() ->
                new RuntimeException(String.format("Account with id %s " +
                "not found", query.getId())));
        return List.of(bankAccountEntity);
    }

    @Override
    public List<BaseEntity> handle(FindAccountWithBalanceQuery query) {
        return query.getEqualityType().equals(EqualityType.GREATER_THAN) ?
                accountRepository.findByBalanceGreaterThan(query.getBalance())
                : accountRepository.findByBalanceLessThan(query.getBalance());
    }
}
