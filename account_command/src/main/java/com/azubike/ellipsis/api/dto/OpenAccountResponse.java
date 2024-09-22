package com.azubike.ellipsis.api.dto;

import com.azubike.ellipsis.dto.BaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class OpenAccountResponse extends BaseResponse {
    private String id;
}
