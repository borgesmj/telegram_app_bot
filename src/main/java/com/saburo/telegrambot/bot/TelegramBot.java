package com.saburo.telegrambot.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.saburo.telegrambot.config.EnvConfig;
import com.saburo.telegrambot.database.CreateTablesCommands;
import com.saburo.telegrambot.database.DatabaseConnection;

import java.sql.Connection;

public class TelegramBot extends TelegramLongPollingBot {
    // Declaramos connection e inicializamos en el constructor
    private final Connection connection;

    // Constructor
    public TelegramBot() {
        // Inicializa la conexión a la base de datos en el constructor
        this.connection = DatabaseConnection.getConnection();
        // CRea las tablas de la base de datos
        CreateTablesCommands createTablesCommands = new CreateTablesCommands(connection);
        createTablesCommands.setTimeZone();
        createTablesCommands.createTablesCommand();
    }

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
