package com.azubike.ellipsis.api.queries;

import com.azubike.ellipsis.queries.BaseQuery;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class FindAccountByHolderQuery extends BaseQuery {
    private String accountHolder;
}
