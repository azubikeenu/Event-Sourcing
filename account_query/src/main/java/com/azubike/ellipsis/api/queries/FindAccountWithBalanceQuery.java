package com.azubike.ellipsis.api.queries;


import com.azubike.ellipsis.api.enums.EqualityType;
import com.azubike.ellipsis.queries.BaseQuery;
import lombok.*;


@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FindAccountWithBalanceQuery extends BaseQuery {
    private EqualityType equalityType;
    private double balance;
}
