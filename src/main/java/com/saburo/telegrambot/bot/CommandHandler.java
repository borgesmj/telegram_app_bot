package com.saburo.telegrambot.bot;
/**
 * Clase que recibe un mensaje de @link TelegramBot si es un comando con /
 * @param newMessage Mensaje recibido
 */
public class CommandHandler {
    private String newMessage;
    public CommandHandler(String newMessage){
        this.newMessage = newMessage;
    }

    public void handleCommand(){
        System.out.println("mensaje con comando: "+ newMessage);
    }
}
