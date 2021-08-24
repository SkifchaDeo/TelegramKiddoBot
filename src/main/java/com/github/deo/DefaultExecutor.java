package com.github.deo;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.AbstractSendRequest;
import com.pengrad.telegrambot.request.SendMessage;

public class DefaultExecutor implements CommandExecutor<SendMessage> {
    @Override
    public AbstractSendRequest<SendMessage> execute(Message message, String text) {
        return new SendMessage(message.chat().id(), "No command found");
    }
}
