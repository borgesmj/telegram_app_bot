package com.saburo.telegrambot.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.saburo.telegrambot.config.EnvConfig;

public class TelegramBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return EnvConfig.get("TELEGRAM_BOT_USERNAME");
    }

    @Override
    public String getBotToken() {
        return EnvConfig.get("TELEGRAM_BOT_TOKEN");
    }

    @Override
    public void onUpdateReceived(Update update) {
        MessageListener messageListener = new MessageListener();
        var message = messageListener.handlMessage(update);
        if (message.getText().startsWith("/")) {
            CommandHandler commandHandler = new CommandHandler(message.getText());
            commandHandler.handleCommand();
        } else {
            MessageHandler messageHandler = new MessageHandler(message.getText());
            messageHandler.handleMessage();
        }
    }
}
