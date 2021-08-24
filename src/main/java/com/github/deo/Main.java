package com.github.deo;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        KiddoBot telegramBot = new KiddoBot(System.getenv("TelegramBotKey"));
        telegramBot.start();
    }
}
