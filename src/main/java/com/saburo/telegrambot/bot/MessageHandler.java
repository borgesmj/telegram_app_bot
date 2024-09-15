package com.saburo.telegrambot.bot;
/**
 * Clase que recibe un mensaje de @link TelegramBot si no es un comando con /
 * @param newMessage Mensaje recibido
 */
public class MessageHandler {
    private String newMessage;
    public MessageHandler(String newMessage){
        this.newMessage = newMessage;
    }

    public void handleMessage(){
        System.out.println("mensaje sin comando: "+ newMessage);
    }
}
