package com.azubike.ellipsis.domain;

import com.azubike.ellipsis.dto.AccountType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class BankAccountEntity extends BaseEntity  {
    @Id
    private String id ;
     private String accountHolder ;
     private Date creationDate;
     @Enumerated(EnumType.STRING)
     private AccountType accountType ;
     private double balance ;

}
