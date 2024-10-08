package com.azubike.ellipsis.api.queries;

import com.azubike.ellipsis.domain.BaseEntity;

import java.util.List;

public interface QueryHandler{
    List<BaseEntity> handle(FindAllAccountQuery query);
    List<BaseEntity> handle(FindAccountByHolderQuery query);
    List<BaseEntity> handle(FindAccountByIdQuery query);
    List<BaseEntity> handle(FindAccountWithBalanceQuery query);
}
