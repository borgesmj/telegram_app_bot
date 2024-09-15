package com.saburo.telegrambot.bot;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Clase que maneja los mensajes recibidos por el bot de Telegram.
 * @params update Objeto que contiene la información del mensaje recibido.
 * recibe parámetros de @link TelegramBot
 * @return un objeto de tipo Message que contiene la información del mensaje.
 */
public class MessageListener {
    public Message handlMessage(Update update){
        var newMessage = new Message();
        if(update.hasMessage()){
            newMessage = update.getMessage();
        }
        return newMessage;
    }
}
