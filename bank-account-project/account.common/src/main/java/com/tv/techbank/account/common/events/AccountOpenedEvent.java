package com.tv.techbank.account.common.events;

import com.tv.techbank.account.common.dto.AccountType;
import com.tv.techbank.cqrs.core.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AccountOpenedEvent extends BaseEvent {
    private String accountHolder;
    private AccountType accountType;
    private Date createdDate;
    private double openingBalance;
}
