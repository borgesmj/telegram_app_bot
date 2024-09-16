package com.saburo.telegrambot.bot;

import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Clase que recibe un mensaje de @link TelegramBot si no es un comando con /
 * @param newMessage Mensaje recibido en objeto Message
 */
public class MessageHandler {
    private Message newMessage;
    private MessageSender messageSender;
    public MessageHandler(Message newMessage, MessageSender messageSender){
        this.newMessage = newMessage;
        this.messageSender = messageSender;
    }

    public void handleMessage(){
        messageSender.sendMessage(newMessage, "*texto sin comando*");
    }
}
