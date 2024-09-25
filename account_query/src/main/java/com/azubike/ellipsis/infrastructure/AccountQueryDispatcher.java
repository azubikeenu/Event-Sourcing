package com.azubike.ellipsis.infrastructure;

 import com.azubike.ellipsis.domain.BaseEntity;
 import com.azubike.ellipsis.queries.BaseQuery;
import com.azubike.ellipsis.queries.QueryHandlerMethod;
 import org.springframework.stereotype.Component;

 import java.util.*;

@Component
public class AccountQueryDispatcher implements QueryDispatcher {
    Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseQuery> void register(Class<T> type, QueryHandlerMethod<T> handlerMethod) {
        var handlers = routes.computeIfAbsent(type, k -> new LinkedList<>());
        handlers.add(handlerMethod);
    }

    @Override
    public <T extends BaseQuery , U extends BaseEntity> List<U> send(T query) {
        List<QueryHandlerMethod> queryHandlerMethods = routes.get(query.getClass());
        if (queryHandlerMethods == null || queryHandlerMethods.isEmpty()) {
            throw new RuntimeException("No query handler was registered for method");
        }
        if (queryHandlerMethods.size() > 1) {
            throw new RuntimeException("Too many query handler methods");
        }

        return queryHandlerMethods.get(0).handle(query);
    }
}
