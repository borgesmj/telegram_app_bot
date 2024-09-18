package com.saburo.telegrambot.bot;

import org.telegram.telegrambots.meta.api.objects.Message;
import com.saburo.telegrambot.database.DatabaseCommands;
import com.saburo.telegrambot.user.UserProfile;
import com.saburo.telegrambot.user.UserStatus;

import static com.saburo.telegrambot.bot.TelegramBotContent.*;

/**
 * Clase que recibe un mensaje de @link TelegramBot si es un comando con /
 * 
 * @param newMessage       Mensaje recibido en objeto Message
 * @param messageSender    Instancia de MessageSender
 * @param databaseCommands Instancia de DatabaseCommands
 * @param userStatus       Instancia de UserStatus
 * @param userProfile      Instancia de UserProfile
 */
public class CommandHandler {
    private Message newMessage;
    private MessageSender messageSender;
    private DatabaseCommands databaseCommands;
    private UserProfile userProfile;
    private UserStatus userStatus;

    public CommandHandler(Message newMessage, MessageSender messageSender, DatabaseCommands databaseCommands, UserStatus userStatus, UserProfile userProfile) {
        this.newMessage = newMessage;
        this.messageSender = messageSender;
        this.databaseCommands = databaseCommands;
        this.userProfile = userProfile;
        this.userStatus = userStatus;
    }
/**
 * Maneja los comandos recibidos a través de mensajes de Telegram.
 * Este método procesa diferentes comandos y ejecuta las acciones correspondientes
 * dependiendo del texto del mensaje.
 *
 * @param newMessage       Mensaje recibido como un objeto {@link Message}.
 * @param messageSender    Instancia de {@link MessageSender} para enviar respuestas al usuario.
 * @param databaseCommands Instancia de {@link DatabaseCommands} para interactuar con la base de datos.
 */
    public void handleCommand() {
        // Obtiene el texto del mensaje para determinar qué comando ejecutar.
        switch (newMessage.getText()) {
            case "/menu":
                messageSender.sendMessage(newMessage, MENU_PRINCIPAL);
            break;
            case "/start":
             /**
             * Comando /start:
             * Verifica si el usuario es nuevo, es decir, si su ID de Telegram ya existe en la base de datos.
             * - Si es un usuario nuevo, envía un mensaje de bienvenida y, si el username está disponible, lo utiliza.
             * - Si no hay un username disponible, solicita que el usuario lo proporcione.
             * - Si ya existe el usuario, envía un mensaje indicando que el usuario ya está registrado.
             */
                boolean isNewUser = databaseCommands.checkUserId(newMessage.getFrom().getId());
                /**
                 * Verifica que no haya usuario registrado en la base de datos ejecutanto el
                 * metodo checkUserId en @link DatabaseCommands
                 * 
                 * @return true o false
                 * 
                 */
                String username = newMessage.getFrom().getUserName();
                if (isNewUser) {
                    messageSender.sendMessage(newMessage, USER_MSG_1);
                    if (username != null) {
                        messageSender.sendMessage(newMessage, TelegramBotContent.USER_MSG_2(username));
                    } else{
                        messageSender.sendMessage(newMessage, USER_MSG_3);
                        userStatus.setIsWaitingForNewUsername(newMessage.getFrom().getId(), true);
                    }
                } else {
                    username = databaseCommands.getCurrentUsername(newMessage.getFrom().getId());
                    userProfile.setUsername(username);
                    databaseCommands.updateLastLogin(newMessage.getFrom().getId());
                    messageSender.sendMessage(newMessage, TelegramBotContent.USER_MSG_7(username));
                }
                break;
                // comando por el cual el usario indica que quiere dejar el nombre de usuario como lo tiene en su perfil de telegram
            case "/estabienasi":
                userProfile.setUsername(newMessage.getFrom().getUserName());
                databaseCommands.insertNewUser(newMessage.getFrom().getId(), newMessage.getFrom().getUserName());
                messageSender.sendMessage(newMessage, TelegramBotContent.USER_MSG_6(userProfile.getUsername()));
                userStatus.setIsWaitingForNewCapital(newMessage.getFrom().getId(), true);
                messageSender.sendMessage(newMessage, USER_MSG_8);
                break;
                // comando por el cual el usario indica que quiere cambiar el nombre de usuario
            case "/cambiarlo":
                messageSender.sendMessage(newMessage, USER_MSG_4);
                userStatus.setIsWaitingForNewUsername(newMessage.getFrom().getId(), true);
            break;
            case "/nuevoingreso":
                messageSender.sendMessage(newMessage, "nuevo ingreso. ingresa el monto");
                userStatus.setTypeOfMovement(newMessage.getFrom().getId(), "INGRESO");
                userStatus.setIsWaitingForNewAmmount(newMessage.getFrom().getId(), true);
            break;
            case "/nuevoegreso":
                messageSender.sendMessage(newMessage, "nuevo Egreso. ingresa el monto");
                userStatus.setTypeOfMovement(newMessage.getFrom().getId(), "EGRESO");
                userStatus.setIsWaitingForNewAmmount(newMessage.getFrom().getId(), true);
            break;
            default:
            if (userStatus.getIsWaitingForCategory(newMessage.getFrom().getId())){
                userProfile.setCategory(newMessage.getText().replace("/", "").trim().toUpperCase());
                databaseCommands.saveNewMovement(
                    newMessage.getFrom().getId(),
                    userProfile.getAmmount(),
                    userProfile.getMovementDetails(),
                    userProfile.getCategory(),
                    userStatus.getTypeOfMovement(newMessage.getFrom().getId())
                );
                messageSender.sendMessage(newMessage, "movimiento guardado");
                userStatus.setIsWaitingForCategory(newMessage.getFrom().getId(), false);
                userStatus.setIsWaitingForDetails(newMessage.getFrom().getId(), false);
                userStatus.setIsWaitingForNewAmmount(newMessage.getFrom().getId(), false);
            }
                break;
        }
    }
}
