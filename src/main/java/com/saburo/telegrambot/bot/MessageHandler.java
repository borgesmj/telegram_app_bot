package com.saburo.telegrambot.bot;

import org.telegram.telegrambots.meta.api.objects.Message;

import com.saburo.telegrambot.database.DatabaseCommands;
import com.saburo.telegrambot.user.UserProfile;
import com.saburo.telegrambot.user.UserReports;
import com.saburo.telegrambot.user.UserStatus;
import static com.saburo.telegrambot.bot.TelegramBotContent.*;

import java.util.Arrays;

/**
 * Clase que recibe un mensaje de @link TelegramBot si no es un comando con /
 * 
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
    // private ErrorsHandler errorsHandler;
    // private UserReports userReports;

    public MessageHandler(Message newMessage, MessageSender messageSender, DatabaseCommands databaseCommands,
            UserStatus userStatus, UserProfile userProfile, ErrorsHandler errorsHandler, UserReports userReports) {
        this.newMessage = newMessage;
        this.messageSender = messageSender;
        this.databaseCommands = databaseCommands;
        this.userProfile = userProfile;
        this.userStatus = userStatus;
        // this.errorsHandler = errorsHandler;
        // this.userReports = userReports;
    }

    public void handleMessage() {
        /**
         * Si el usuario ha indicado que quiere cambiar el nombre de usuario
         * se coloca el status a true en @link UserStatus isWaitingForNewUsername
         * y espera que la respuesta que introduzca el usuario sea el nuevo nombre de
         * usuario.
         */

        if (userStatus.getIsWaitingForNewUsername(newMessage.getFrom().getId())) {
            // obtiene el texto del usuario y lo guarda en el perfil
            /**
             * NOTA: este metodo llevarlo directo a la base de datos, y obtener el username
             * desde alli con el setter de @link @userProfile.setUsername
             * 
             */

            // inserta el nuevo nombre de usuario en la base de datos
            String errorMessage = databaseCommands.updateUsername(newMessage.getFrom().getId(), newMessage.getText());
            // si el nombre de usuario no se encuentra en la base de datos, el metodo
            // retorna "", de lo contrario, retorna un mensaje que se enviará al usuario y
            // mantiene el estado de isWaitingForNewUsername en true, hasta que coloque un
            // nombre de usuario válido
            if (errorMessage != "") {
                messageSender.sendMessage(newMessage, USER_MSG_5);
            } else {
                userProfile.setUsername(newMessage.getText());
                userStatus.setIsWaitingForNewUsername(newMessage.getFrom().getId(), false);
                messageSender.sendMessage(newMessage, USER_MSG_6(newMessage.getText()));
                messageSender.sendMessage(newMessage, USER_MSG_8);
                userStatus.setIsWaitingForNewCapital(newMessage.getFrom().getId(), true);
            }
        } else if (userStatus.getIsWaitingForNewCapital(newMessage.getFrom().getId())) {
            boolean isNumber = userProfile.setAmmount(newMessage.getText());
            if (isNumber) {
                databaseCommands.saveInitialCapital(newMessage.getFrom().getId(), userProfile.getAmmount());
                messageSender.sendMessage(newMessage, USER_MSG_9);
                userStatus.setIsWaitingForNewCapital(newMessage.getFrom().getId(), false);
                userStatus.setIsWaitingForInitialSavings(newMessage.getFrom().getId(), true);

            } else {
                messageSender.sendMessage(newMessage, ERROR_MESSAGE);
            }
        } else if (userStatus.getIsWaitingForInitialSavings(newMessage.getFrom().getId())) {
            boolean isNumber = userProfile.setAmmount(newMessage.getText());
            if (isNumber) {
                databaseCommands.saveIniatialSavings(newMessage.getFrom().getId(), userProfile.getAmmount());
                messageSender.sendMessage(newMessage, USER_MSG_10);
                userStatus.setIsWaitingForInitialSavings(newMessage.getFrom().getId(), false);
                userStatus.setIsWaitingForCategories(newMessage.getFrom().getId(), true);
                userStatus.setTypeOfMovement(newMessage.getFrom().getId(), "INGRESO");
            } else {
                messageSender.sendMessage(newMessage, ERROR_MESSAGE);
            }
        } else if (userStatus.getIsWaitingForCategories(newMessage.getFrom().getId())) {
            if (userStatus.getTypeOfMovement(newMessage.getFrom().getId()).equals("INGRESO")) {
                String userText = newMessage.getText();
                String[] userTextArray = userText.trim().split(",");
                for (String category : userTextArray) {
                    databaseCommands.saveCategories(newMessage.getFrom().getId(), category.trim().toUpperCase(),
                            userStatus.getTypeOfMovement(newMessage.getFrom().getId()));
                }
                userStatus.setTypeOfMovement(newMessage.getFrom().getId(), "EGRESO");
                messageSender.sendMessage(newMessage, USER_MSG_11);
            } else {
                String userText = newMessage.getText();
                String[] userTextArray = userText.trim().split(",");
                for (String category : userTextArray) {
                    databaseCommands.saveCategories(newMessage.getFrom().getId(), category.trim().toUpperCase(),
                            userStatus.getTypeOfMovement(newMessage.getFrom().getId()));
                }
                userStatus.setIsWaitingForCategories(newMessage.getFrom().getId(), false);
                messageSender.sendMessage(newMessage, USER_MSG_12);
            }
            /*
             * Esta seccion evalua los estados del usuario para la entrada de informacion
             * isWaitingForNewAmmount @link UserStatus espera el monto de la transaccion
             * isWaitingForDetails @link UserStatus espera que el usuario ingrese los
             * detalles de la transaccion
             * isWaitingForCategory @link UserStatus espera que el usuario ingrese la
             * categoria de la transaccion
             * formateadas en una lista con /, lo cual trata de comando y lo procesará por
             * medio de @link CommandHandler
             */
        } else if (userStatus.getIsWaitingForNewAmmount(newMessage.getFrom().getId())) {
            boolean isNumber = userProfile.setAmmount(newMessage.getText());
            if (isNumber) {
                if (userStatus.getTypeOfMovement(newMessage.getFrom().getId()).equals("EGRESO")
                        && userProfile.getAmmount() > userProfile.getUserCapital()) {
                    messageSender.sendMessage(newMessage,
                            USER_MSG_26);
                            messageSender.sendMessage(newMessage, USER_MSG_21);
                    return;
                } else {
                    messageSender.sendMessage(newMessage, USER_MSG_15);
                    userStatus.setIsWaitingForNewAmmount(newMessage.getFrom().getId(), false);
                    userStatus.setIsWaitingForDetails(newMessage.getFrom().getId(), true);
                }
            } else {
                messageSender.sendMessage(newMessage, ERROR_MESSAGE);
            }
            /**
             * isWaitingForDetails @link UserStatus espera que el usuario ingrese los
             * detalles de la transaccion
             */
        } else if (userStatus.getIsWaitingForDetails(newMessage.getFrom().getId())) {
            userProfile.setMovementDetails(newMessage.getText());
            userStatus.setIsWaitingForDetails(newMessage.getFrom().getId(), false);
            messageSender.sendMessage(newMessage, "Elige la categoria");
            String[] categorias = databaseCommands.getCategories(newMessage.getFrom().getId(),
                    userStatus.getTypeOfMovement(newMessage.getFrom().getId())).toArray(new String[0]);
            if (categorias.length > 0) {
                messageSender.sendMessage(newMessage, TelegramBotContent.USER_MSG_16(Arrays.asList(categorias)));
            } else {
                messageSender.sendMessage(newMessage, USER_MSG_17);
            }
            userStatus.setIsWaitingForCategory(newMessage.getFrom().getId(), true);
        } else if (userStatus.getIsWaitingForNewSavingsAmmount(newMessage.getFrom().getId())) {
            boolean isNumber = userProfile.setAmmount(newMessage.getText());
            if (isNumber) {

                databaseCommands.savingsDeposit(
                        newMessage.getFrom().getId(),
                        userProfile.getAmmount(),
                        userStatus.getTypeOfMovement(newMessage.getFrom().getId()));
                userStatus.setIsWaitingForNewSavingsAmmount(newMessage.getFrom().getId(), false);
                messageSender.sendMessage(newMessage, USER_MSG_20);
                messageSender.sendMessage(newMessage, MENU_PRINCIPAL);
            } else {
                messageSender.sendMessage(newMessage, ERROR_MESSAGE);
            }
        } else if (userStatus.getIsWaitingForEditUsername(newMessage.getFrom().getId())) {
            String errorMessage = databaseCommands.updateUsername(newMessage.getFrom().getId(), newMessage.getText());
            if (errorMessage != "") {
                messageSender.sendMessage(newMessage, USER_MSG_5);
            } else {
                userProfile.setUsername(newMessage.getText());
                userStatus.setIsWaitingForEditUsername(newMessage.getFrom().getId(), false);
                messageSender.sendMessage(newMessage, USER_MSG_6(newMessage.getText()));
                messageSender.sendMessage(newMessage, USER_MSG_21);
            }
        } else if (userStatus.getIsWaitingForNewCategoryName(newMessage.getFrom().getId())) {
            databaseCommands.saveCategories(
                    newMessage.getFrom().getId(),
                    newMessage.getText(),
                    userStatus.getTypeOfMovement(newMessage.getFrom().getId()));
            userStatus.setIsWaitingForNewCategoryName(newMessage.getFrom().getId(), false);
            messageSender.sendMessage(newMessage, "nueva categoria guardada");
        } else if (userStatus.getIsWaitingForSavingsWithdrawAmmount(newMessage.getFrom().getId())) {
            userProfile.setAmmount(newMessage.getText());
            if (userProfile.getAmmount() > userProfile.getUserSavings()) {
                messageSender.sendMessage(newMessage,
                        USER_MSG_25);
                messageSender.sendMessage(newMessage, USER_MSG_21);
                return;
            } else {
                databaseCommands.savingsWithdraw(
                        newMessage.getFrom().getId(),
                        userProfile.getAmmount());
                messageSender.sendMessage(newMessage,
                        "Listo, realizaste un retiro de " + userProfile.getAmmount() + " disfrutalos");
                userStatus.setIsWaitingForSavingsWithdrawAmmount(newMessage.getFrom().getId(), false);
                messageSender.sendMessage(newMessage, MENU_PRINCIPAL);
            }
        }
    }
}
