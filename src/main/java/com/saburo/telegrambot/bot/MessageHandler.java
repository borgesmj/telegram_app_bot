package com.saburo.telegrambot.bot;

import org.telegram.telegrambots.meta.api.objects.Message;

import com.saburo.telegrambot.database.DatabaseCommands;
import com.saburo.telegrambot.user.UserProfile;
import com.saburo.telegrambot.user.UserStatus;
import static com.saburo.telegrambot.bot.TelegramBotContent.*;

/**
 * Clase que recibe un mensaje de @link TelegramBot si no es un comando con /
 * @param newMessage       Mensaje recibido en objeto Message
 * @param messageSender    Instancia de MessageSender
 * @param databaseCommands Instancia de DatabaseCommands
 * @param userStatus       Instancia de UserStatus
 * @param userProfile      Instancia de UserProfile
 */
public class MessageHandler {
    private Message newMessage;
    private MessageSender messageSender;
    private DatabaseCommands databaseCommands;
    private UserStatus userStatus;
    private UserProfile userProfile;

    public MessageHandler(Message newMessage, MessageSender messageSender, DatabaseCommands databaseCommands, UserStatus userStatus){
        this.newMessage = newMessage;
        this.messageSender = messageSender;
        this.databaseCommands = databaseCommands;
        this.userProfile = new UserProfile(newMessage.getFrom().getId());
        this.userStatus = userStatus;
    }
    public void handleMessage(){
    /**
     * Si el usuario ha indicado que quiere cambiar el nombre de usuario
     * se coloca el status a true en @link UserStatus isWaitingForNewUsername
     * y espera que la respuesta que introduzca el usuario sea el nuevo nombre de usuario.
     */

        if(userStatus.getIsWaitingForNewUsername(newMessage.getFrom().getId())){
            // obtiene el texto del usuario y lo guarda en el perfil
            userProfile.setUsername(newMessage.getText());
            // inserta el nuevo nombre de usuario en la base de datos
            String errorMessage =  databaseCommands.insertNewUser(newMessage.getFrom().getId(), newMessage.getText());
            // si el nombre de usuario no se encuentra en la base de datos, el metodo retorna "", de lo contrario, retorna un mensaje que se enviará al usuario y mantiene el estado de isWaitingForNewUsername en true, hasta que coloque un nombre de usuario válido
            if(errorMessage != ""){
                messageSender.sendMessage(newMessage, USER_MSG_5);
            } else{
                userStatus.setIsWaitingForNewUsername(newMessage.getFrom().getId(), false);
                messageSender.sendMessage(newMessage, USER_MSG_6(userProfile.getUsername()));
                messageSender.sendMessage(newMessage, USER_MSG_8);
                userStatus.setIsWaitingForNewCapital(newMessage.getFrom().getId(), true);
            }
        } else if (userStatus.getIsWaitingForNewCapital(newMessage.getFrom().getId())){
            userProfile.setAmmount(newMessage.getText());
            databaseCommands.saveInitialCapital(newMessage.getFrom().getId(), userProfile.getAmmount());
            messageSender.sendMessage(newMessage, "guardado");
            userStatus.setIsWaitingForNewCapital(newMessage.getFrom().getId(), false);
        } 
    }
}
