package com.saburo.telegrambot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import com.saburo.telegrambot.bot.TelegramBot;;

public class Main {
    public static void main(String[] args)throws TelegramApiException {
        try {
            TelegramBotsApi telegramBot = new TelegramBotsApi(DefaultBotSession.class);
            telegramBot.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}