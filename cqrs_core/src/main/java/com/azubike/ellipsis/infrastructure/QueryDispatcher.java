package com.azubike.ellipsis.infrastructure;
import com.azubike.ellipsis.domain.BaseEntity;
import com.azubike.ellipsis.queries.BaseQuery;
import com.azubike.ellipsis.queries.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {
    <T extends BaseQuery> void register(Class<T> type, QueryHandlerMethod<T> handlerMethod);

    <T extends BaseQuery , U extends BaseEntity> List<U> send(T query);
}
