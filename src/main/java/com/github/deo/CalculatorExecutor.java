package com.github.deo;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.AbstractSendRequest;
import com.pengrad.telegrambot.request.SendMessage;

public class CalculatorExecutor implements CommandExecutor<SendMessage>{
    private String execute(String text) {
        try {
            DoubleEvaluator eval = new DoubleEvaluator();
            return String.valueOf(eval.evaluate(text));
        }
        catch (IllegalArgumentException exception){
            return "It's not an Expression/Constant/Number";
        }
    }

    @Override
    public AbstractSendRequest<SendMessage> execute(Message message, String text) {
        return new SendMessage(message.chat().id(), execute(text));
    }
}
