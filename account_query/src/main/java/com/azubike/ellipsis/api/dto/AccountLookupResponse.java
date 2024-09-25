package com.azubike.ellipsis.api.dto;

import com.azubike.ellipsis.domain.BankAccountEntity;
import com.azubike.ellipsis.dto.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountLookupResponse extends BaseResponse {
   List<BankAccountEntity> bankAccounts;
}
