package com.tv.techbank.account.cmd.api.commands;

import com.tv.techbank.cqrs.core.command.BaseCommand;
import lombok.Data;

@Data
public class WithdrawFundsCommand extends BaseCommand {
    private double amount;
}
