package com.saburo.telegrambot.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * MessageSender es la clase encargada de enviar mensajes de respuesta a los
 * usuarios en Telegram.
 * Utiliza un bot de Telegram para ejecutar el envío de los mensajes.
 */
public class MessageSender {
    // Se inicializa el bot
    private final TelegramLongPollingBot bot;

    /**
     * Constructor de la clase MessageSender.
     * 
     * @param bot una instancia de TelegramLongPollingBot, utilizada para enviar
     *            mensajes a los usuarios.
     */
    public MessageSender(TelegramLongPollingBot bot) {
        this.bot = bot;
    }

    /**
     * Envía un mensaje de texto a un usuario específico de Telegram.
     * 
     * @param message El objeto Message que contiene los datos del mensaje recibido,
     *                de donde se extrae el ID del chat.
     * @param text    El texto que se va a enviar como respuesta.
     * @throws Exception si ocurre algún problema al ejecutar el envío del mensaje.
     */
    public void sendMessage(Message message, String text) {
        // Inicializamos el método sendMessage importado de la API de Telegram
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        sendMessage.enableMarkdown(true);
        sendMessage.disableWebPagePreview();

        // Intentamos enviar el mensaje
        try {
           bot.execute(sendMessage);
        } catch (Exception e) {
            System.err.println("Error al enviar el mensaje: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendAdminMessage(String userId, String Text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(userId);
        sendMessage.setText(Text);
        try {
            bot.execute(sendMessage);
        } catch (Exception e) {
            System.err.println("Error al enviar el mensaje: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
