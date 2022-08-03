package com.tv.techbank.account.cmd.api.commands;

import com.tv.techbank.cqrs.core.command.BaseCommand;

public class CloseAccountCommand  extends BaseCommand {
    public CloseAccountCommand(String id){
        super(id);
    }
}
