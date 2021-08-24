package com.github.deo;

import java.util.Optional;

public class CommandDispatcher {
    public static CommandExecutor<?> getExecutor(Optional<String> command) {
        if (command.isEmpty()){
            return new DefaultExecutor();
        }
        if (command.get().equals("calc"))
            return new CalculatorExecutor();
        if (command.get().equals("meme"))
            return new MemeExecutor();
        return new DefaultExecutor();
    }
}
