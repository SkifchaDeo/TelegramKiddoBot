package com.github.deo;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import kotlin.Pair;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KiddoBot {

    private Thread thread;
    private TelegramBot bot;
    private int offset = 0;


    public KiddoBot(String key) {
        thread = new Thread(this::mainLoop);
        bot = new TelegramBot(key);
    }

    private void mainLoop() {
        try {
            while (true) {
                work();
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void work() {
        getUpdates().stream().map(Update::message).forEach(message -> {
            Pair<Optional<String>, String> command = getCommand(message.text());
            //System.out.println(command);
            bot.execute(CommandDispatcher.getExecutor(command.getFirst()).execute(message, command.getSecond()));
        });
    }

    private List<Update> getUpdates() {
        GetUpdates getUpdates = new GetUpdates().limit(100).offset(offset).timeout(0);
        GetUpdatesResponse updatesResponse = bot.execute(getUpdates);
        List<Update> updates = updatesResponse.updates();
        if (updates.size() != 0)
            offset = updates.get(updates.size() - 1).updateId() + 1;
        return updates;
    }

    private Pair<Optional<String>, String> getCommand(String text) {
        Pattern p = Pattern.compile("/(\\w+)( (.*))*");
        Matcher m = p.matcher(text);
        if (m.matches()) {
            return new Pair<>(Optional.of(m.group(1)), m.group(3));
        }
        return new Pair<>(Optional.empty(), text);
    }

    public void start() throws InterruptedException {
        thread.start();
        thread.join();
    }

    public void stop() {
        thread.interrupt();
    }
}
