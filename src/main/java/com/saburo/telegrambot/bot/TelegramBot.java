package com.saburo.telegrambot.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.saburo.telegrambot.config.EnvConfig;
import com.saburo.telegrambot.database.CreateTablesCommands;
import com.saburo.telegrambot.database.DatabaseCommands;
import com.saburo.telegrambot.database.DatabaseConnection;
import com.saburo.telegrambot.user.UserStatus;
import com.saburo.telegrambot.user.UserProfile;
import com.saburo.telegrambot.user.UserReports;

import java.sql.Connection;

/**
 * Clase que representa el bot de Telegram y maneja la recepción y el
 * procesamiento
 * de actualizaciones (mensajes) enviadas al bot. Extiende
 * {@link TelegramLongPollingBot}
 * para recibir actualizaciones mediante polling.
 * 
 * Esta clase se encarga de:
 * - Inicializar la conexión a la base de datos y crear las tablas necesarias.
 * - Configurar y enviar mensajes a los usuarios.
 * - Procesar los comandos y mensajes de los usuarios.
 */
public class TelegramBot extends TelegramLongPollingBot {
    // Conexión a la base de datos para manejar datos relacionados con el bot
    private final Connection connection;
    // Instancia de MessageSender para enviar mensajes de respuesta
    private final MessageSender messageSender;
    // instancia de DatabaseCommands
    private final DatabaseCommands databaseCommands;
    // Instancia de UserStatus
    private final UserStatus userStatus;
    // Instancia de UserProfile
    private final UserProfile userProfile;
    // Instancia de manejo de errores
    private final ErrorsHandler errorsHandler;
    // Instancia de manejo de reportes
    private final UserReports userReports;

    /**
     * Constructor de la clase TelegramBot.
     * Inicializa la conexión a la base de datos, crea las tablas necesarias y
     * configura el objeto MessageSender.
     */
    public TelegramBot() {
        // Inicializa la conexión a la base de datos mediante la clase
        // DatabaseConnection
        this.connection = DatabaseConnection.getConnection();
        // Crea las tablas necesarias en la base de datos usando CreateTablesCommands
        CreateTablesCommands createTablesCommands = new CreateTablesCommands(connection);
        createTablesCommands.createTablesCommand();
        // Inicializa el MessageSender con la instancia actual del bot
        messageSender = new MessageSender(this);
        // Iniciliza databaseCommands enviando la conexión a la base de datos
        databaseCommands = new DatabaseCommands(connection);
        // Iniciliza userStatus
        userStatus = new UserStatus();
        // Inicializa userProfile
        userProfile = new UserProfile();
        // Inicizaliza errorsHandler
        errorsHandler = new ErrorsHandler();
        // Inicializamos userReports
        userReports = new UserReports(connection);
    }

    /**
     * Obtiene el nombre de usuario del bot configurado en las variables de entorno.
     * 
     * @return El nombre de usuario del bot.
     */
    @Override
    public String getBotUsername() {
        return EnvConfig.get("TELEGRAM_BOT_USERNAME");
    }

    /**
     * Obtiene el token del bot configurado en las variables de entorno.
     * 
     * @return El token del bot.
     */
    @Override
    public String getBotToken() {
        return EnvConfig.get("TELEGRAM_BOT_TOKEN");
    }

    public static String getAdminChatId(){
        return EnvConfig.get("ADMIN_CHAT_ID");
    }

    /**
     * Maneja las actualizaciones (mensajes) recibidas por el bot.
     * 
     * @param update El objeto Update que contiene la información de la
     *               actualización recibida.
     */
    @Override
    public void onUpdateReceived(Update update) {
        // Crea una instancia de MessageListener para extraer el mensaje del objeto
        // Update
        MessageListener messageListener = new MessageListener();
        var message = messageListener.handlMessage(update);
        /*
         * Cambiado el setUsername a extraerlo con cada update
         * 
         */
        String username = databaseCommands.getCurrentUsername(message.getFrom().getId());
        userProfile.setUsername(username);

        // Verifica si el mensaje tiene texto
        if (message.hasText()) {
            // Si el texto del mensaje comienza con "/", se considera un comando
            if (message.getText().startsWith("/")) {
                CommandHandler commandHandler = new CommandHandler(message, messageSender, databaseCommands, userStatus,
                        userProfile, errorsHandler, userReports);
                commandHandler.handleCommand();
            } else {
                // Si el texto del mensaje no comienza con "/", se considera un mensaje normal
                MessageHandler messageHandler = new MessageHandler(message, messageSender, databaseCommands, userStatus,
                        userProfile, errorsHandler, userReports);
                messageHandler.handleMessage();
            }
        } else {
            // Imprime un mensaje en caso de que el mensaje recibido no contenga texto
            System.out.println("No hay texto");
        }
    }
}
