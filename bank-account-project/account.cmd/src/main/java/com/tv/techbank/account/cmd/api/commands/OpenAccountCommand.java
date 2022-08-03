package com.tv.techbank.account.cmd.api.commands;

import com.tv.techbank.account.common.dto.AccountType;
import com.tv.techbank.cqrs.core.command.BaseCommand;
import lombok.Data;

@Data
public class OpenAccountCommand extends BaseCommand {
    private String accountHolderName;
    private AccountType accountType;
    private double openingBalance;
}
