package com.saburo.telegrambot.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.saburo.telegrambot.config.EnvConfig;

public class TelegramBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername(){
        return EnvConfig.get("TELEGRAM_BOT_USERNAME");
    }

    @Override
    public String getBotToken(){
        return EnvConfig.get("TELEGRAM_BOT_TOKEN");
    }

    @Override
    public void onUpdateReceived(Update update){
        System.out.println(update.getMessage().getText());
    }
}
