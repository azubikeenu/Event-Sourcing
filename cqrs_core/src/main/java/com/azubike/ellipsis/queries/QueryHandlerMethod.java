package com.azubike.ellipsis.queries;

import com.azubike.ellipsis.domain.BaseEntity;

import java.util.List;

public interface  QueryHandlerMethod <T extends BaseQuery>{
     List<BaseEntity> handle(T query);
}
