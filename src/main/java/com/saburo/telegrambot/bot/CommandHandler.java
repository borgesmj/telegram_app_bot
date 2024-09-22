package com.saburo.telegrambot.bot;

import org.telegram.telegrambots.meta.api.objects.Message;
import com.saburo.telegrambot.database.DatabaseCommands;
import com.saburo.telegrambot.user.UserProfile;
import com.saburo.telegrambot.user.UserReports;
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
    // private ErrorsHandler errorsHandler;
    private UserReports userReports;
    private String username;

    public CommandHandler(Message newMessage, MessageSender messageSender, DatabaseCommands databaseCommands,
            UserStatus userStatus, UserProfile userProfile, ErrorsHandler errorsHandler, UserReports userReports) {
        this.newMessage = newMessage;
        this.messageSender = messageSender;
        this.databaseCommands = databaseCommands;
        this.userProfile = userProfile;
        this.userStatus = userStatus;
        // this.errorsHandler = errorsHandler;
        this.userReports = userReports;
        this.username = "";

    }

    /**
     * Maneja los comandos recibidos a través de mensajes de Telegram.
     * Este método procesa diferentes comandos y ejecuta las acciones
     * correspondientes
     * dependiendo del texto del mensaje.
     *
     * @param newMessage       Mensaje recibido como un objeto {@link Message}.
     * @param messageSender    Instancia de {@link MessageSender} para enviar
     *                         respuestas al usuario.
     * @param databaseCommands Instancia de {@link DatabaseCommands} para
     *                         interactuar con la base de datos.
     * @param userStatus       Instancia de {@link UserStatus} para mantener el
     *                         estado del usuario.
     * @param userProfile      Instancia de {@link UserProfile} para almacenar
     *                         información del usuario.
     */
    public void handleCommand() {
        // Obtiene el texto del mensaje para determinar qué comando ejecutar.
        switch (newMessage.getText()) {
            case "/menu":
                messageSender.sendMessage(newMessage, MENU_PRINCIPAL);
                username = databaseCommands.getCurrentUsername(newMessage.getFrom().getId());
                userProfile.setUsername(username);
                // con la entrada del comando /menu seteamos todos los estados a false, para qeu
                // el bot no espere ningun texto fuera de un comando
                userStatus.setIsWaitingForCategories(newMessage.getFrom().getId(), false);
                userStatus.setIsWaitingForDetails(newMessage.getFrom().getId(), false);
                userStatus.setIsWaitingForNewAmmount(newMessage.getFrom().getId(), false);
                userStatus.setIsWaitingForNewCapital(newMessage.getFrom().getId(), false);
                userStatus.setIsWaitingForInitialSavings(newMessage.getFrom().getId(), false);
                userStatus.setIsWaitingForNewUsername(newMessage.getFrom().getId(), false);
                userStatus.setIsWaitingForNewSavingsAmmount(newMessage.getFrom().getId(), false);
                userStatus.setIsWaitingForCategories(newMessage.getFrom().getId(), false);
                userStatus.setTypeOfMovement(newMessage.getFrom().getId(), "");
                userStatus.setIsWaitingForMonth(newMessage.getFrom().getId(), false);
                break;
            case "/start":
                /**
                 * Comando /start:
                 * Verifica si el usuario es nuevo, es decir, si su ID de Telegram ya existe en
                 * la base de datos.
                 * - Si es un usuario nuevo, envía un mensaje de bienvenida y, si el username
                 * está disponible, lo utiliza.
                 * - Si no hay un username disponible, solicita que el usuario lo proporcione.
                 * - Si ya existe el usuario, envía un mensaje indicando que el usuario ya está
                 * registrado.
                 */
                boolean isNewUser = databaseCommands.checkUserId(newMessage.getFrom().getId());
                /**
                 * Verifica que no haya usuario registrado en la base de datos ejecutanto el
                 * metodo checkUserId en @link DatabaseCommands
                 * 
                 * @return true o false
                 * 
                 */
                username = newMessage.getFrom().getUserName();
                if (isNewUser) {
                    messageSender.sendMessage(newMessage, USER_MSG_1);
                    databaseCommands.insertNewUser(newMessage.getFrom().getId(), newMessage.getFrom().getUserName());
                    if (username != null) {
                        messageSender.sendMessage(newMessage, TelegramBotContent.USER_MSG_2(username));
                    } else {
                        messageSender.sendMessage(newMessage, USER_MSG_3);
                        userStatus.setIsWaitingForNewUsername(newMessage.getFrom().getId(), true);
                    }
                } else {
                    username = databaseCommands.getCurrentUsername(newMessage.getFrom().getId());
                    databaseCommands.updateLastLogin(newMessage.getFrom().getId());
                    messageSender.sendMessage(newMessage, TelegramBotContent.USER_MSG_7(username));
                    messageSender.sendMessage(newMessage, MENU_PRINCIPAL);
                }
                break;
            // comando por el cual el usario indica que quiere dejar el nombre de usuario
            // como lo tiene en su perfil de telegram
            case "/estabienasi":
                userProfile.setUsername(username);
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
            /*
             * nuevo ingreso: el usuario indica que quiere hacer un nuevo ingreso
             * Coloca en true el estado de isWaitingForNewAmmount en @link UserStatus
             * Coloca en INGRESO el tipo de movimiento en @link UserStatus
             * Se evalua el estado en @link MessageSender para enviar el mensaje
             * correspondiente
             * 
             */
            case "/nuevoingreso":
                messageSender.sendMessage(newMessage, USER_MSG_13);
                userStatus.setTypeOfMovement(newMessage.getFrom().getId(), "INGRESO");
                userStatus.setIsWaitingForNewAmmount(newMessage.getFrom().getId(), true);
                break;
            /*
             * nuevo engreso: el usuario indica que quiere hacer un nuevo egreso
             * Coloca en true el estado de isWaitingForNewAmmount en @link UserStatus
             * Coloca en INGRESO el tipo de movimiento en @link UserStatus
             * Se evalua el estado en @link MessageSender para enviar el mensaje
             * correspondiente
             * 
             */
            case "/nuevoegreso":
                messageSender.sendMessage(newMessage, USER_MSG_14);
                userStatus.setTypeOfMovement(newMessage.getFrom().getId(), "EGRESO");
                userStatus.setIsWaitingForNewAmmount(newMessage.getFrom().getId(), true);
                break;
            case "/nuevoahorro":
                userStatus.setTypeOfMovement(newMessage.getFrom().getId(), "AHORROS");
                userStatus.setIsWaitingForNewSavingsAmmount(newMessage.getFrom().getId(), true);
                messageSender.sendMessage(newMessage, "Ingresa el monto del ahorro");
                break;
            /**
             * /reportes regresa un submenu con opciones de reportes
             */
            case "/reportes":
                messageSender.sendMessage(newMessage, SUB_MENU_REPORTES);
                break;
            case "/balancegeneral":
                String newUserReport = userReports.getBalanceGeneral(newMessage.getFrom().getId());
                messageSender.sendMessage(newMessage, newUserReport);
                messageSender.sendMessage(newMessage, USER_MSG_21);
                break;
            case "/ultimosmovimientos":
                String lastMovements = userReports.getUltimosMovimientos(newMessage.getFrom().getId());
                messageSender.sendMessage(newMessage, lastMovements);
                messageSender.sendMessage(newMessage, USER_MSG_21);
                break;
            case "/reportemensual":
                userStatus.setIsWaitingForMonth(newMessage.getFrom().getId(), true);
                messageSender.sendMessage(newMessage, SUB_MENU_MESES);
                messageSender.sendMessage(newMessage, USER_MSG_21);
                break;
            case "/perfil":
                String profileReport = userReports.getProfile(userProfile.getUsername(), newMessage.getFrom().getId());
                messageSender.sendMessage(newMessage, profileReport);
                messageSender.sendMessage(newMessage, USER_MSG_21);
            break;
            case "/editarperfil":
                messageSender.sendMessage(newMessage, SUB_MENU_EDITAR_PERFIL);
            break;
            case "/editarnombre":
                messageSender.sendMessage(newMessage, USER_MSG_4);
                userStatus.setIsWaitingForEditUsername(newMessage.getFrom().getId(), true);
            break;
            default:
                /**
                 * isWaitingForNewCategory de @link UserStatus espera el monto de la transaccion
                 */
                if (newMessage.getText().startsWith("/ver")) {
                    // Obtener la parte del mensaje que contiene el ID
                    String parts = newMessage.getText().replace("/ver", "").trim(); 
                    // Eliminar comando y espacios en blanco
                    // Eliminar ceros iniciales, pero mantener el '0' si es el único carácter
                    String stringId = parts.replaceFirst("^0+(?!$)", "");
                    // Convertir a entero
                    int movementId = 0;
                    try {
                        movementId = Integer.parseInt(stringId); // Intentamos convertir el string a int
                    } catch (NumberFormatException e) {
                        System.out.println("Error: el ID no es válido");
                    }

                    // Si tenemos un ID válido, hacemos el resto de la lógica
                    if (movementId > 0) {
                        System.out.println(movementId);
                        String movementReport = userReports.getMovementById(movementId);
                        messageSender.sendMessage(newMessage, movementReport);
                        messageSender.sendMessage(newMessage, USER_MSG_21);
                        messageSender.sendMessage(newMessage, USER_MSG_22);
                    } else {
                        System.out.println("Error: el ID no es válido o es cero.");
                    }
                } else if (userStatus.getIsWaitingForCategory(newMessage.getFrom().getId())) {
                    userProfile.setCategory(newMessage.getText().replace("/", "").trim().toUpperCase());
                    databaseCommands.saveNewMovement(
                            newMessage.getFrom().getId(),
                            userProfile.getAmmount(),
                            userProfile.getMovementDetails(),
                            userProfile.getCategory(),
                            userStatus.getTypeOfMovement(newMessage.getFrom().getId()));
                    messageSender.sendMessage(newMessage, USER_MSG_18);
                    userStatus.setIsWaitingForCategory(newMessage.getFrom().getId(), false);
                    userStatus.setIsWaitingForDetails(newMessage.getFrom().getId(), false);
                    userStatus.setIsWaitingForNewAmmount(newMessage.getFrom().getId(), false);
                    messageSender.sendMessage(newMessage, MENU_PRINCIPAL);
                } else if (userStatus.getIsWaitingForMonth(newMessage.getFrom().getId())) {
                    // Creamos un integer con el numero del mes ingresado por el usuario
                    userProfile.setMonth(
                            newMessage.getText().replace("/", "").toLowerCase());
                    // Creamos un reporte llamando a la funcion getTotalByMonthCategoryType de @link
                    // UserReports
                    String newReport = userReports.getTotalByMonthCategoryType(
                            newMessage.getFrom().getId(),
                            userProfile.getMonth(),
                            newMessage.getText().replace("/", "").toLowerCase());
                    // Enviamos el mensaje al usuario
                    messageSender.sendMessage(newMessage, newReport);
                    messageSender.sendMessage(newMessage, USER_MSG_21);
                    messageSender.sendMessage(newMessage, USER_MSG_23);
                }
                break;
        }
    }
}
