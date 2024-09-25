package com.azubike.ellipsis.api.queries;

import com.azubike.ellipsis.queries.BaseQuery;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindAccountByIdQuery extends BaseQuery {
    private String id;
}
