package com.saburo.telegrambot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import com.saburo.telegrambot.bot.TelegramBot;

/**
 * Clase principal de la aplicación que se encarga de inicializar y registrar el bot de Telegram.
 * Llama a la clase {@link TelegramBot}, que ejecuta los métodos {@code getBotUsername()} y {@code getBotToken()}.
 * 
 * @author Miguel Borges
 * @version 1.0
 */
public class Main {
    /**
     * Método principal que inicia la aplicación y registra el bot de Telegram usando la API de TelegramBots.
     * 
     * @param args Argumentos pasados desde la línea de comandos (no utilizados en este caso).
     * @throws TelegramApiException Si ocurre un error durante la inicialización o registro del bot.
     */
    public static void main(String[] args) throws TelegramApiException {
        try {
            // Crea una nueva instancia de TelegramBotsApi para manejar sesiones de bots.
            TelegramBotsApi telegramBot = new TelegramBotsApi(DefaultBotSession.class);
            
            // Registra el bot de Telegram creado en la clase TelegramBot.
            telegramBot.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            // Imprime el stack trace de la excepción en caso de error.
            e.printStackTrace();
        }
    }
}
