package org.example.command;

import org.springframework.stereotype.Component;

@Component
public class ExitCommand implements ICommand{
    @Override
    public void execute() {
        System.exit(0);
    }

    @Override
    public String getDescription() {
        return "Выход";
    }
}
