package com.github.deo;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.AbstractSendRequest;

public interface CommandExecutor<T extends AbstractSendRequest<T>> {
    AbstractSendRequest<T> execute(Message message, String text);
}
