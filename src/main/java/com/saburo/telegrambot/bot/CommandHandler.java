package com.saburo.telegrambot.bot;

import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Clase que recibe un mensaje de @link TelegramBot si es un comando con /
 * @param newMessage Mensaje recibido en objeto Message
 */
public class CommandHandler {
    private Message newMessage;
    private MessageSender messageSender;
    public CommandHandler(Message newMessage, MessageSender messageSender){
        this.newMessage = newMessage;
        this.messageSender = messageSender;
    }

    public void handleCommand(){
        messageSender.sendMessage(newMessage, "texto con comando");
    }
}
