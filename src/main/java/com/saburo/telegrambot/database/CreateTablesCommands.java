package com.saburo.telegrambot.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * Clase para crear las tablas de la base de datos
 * y se llama en la clase principal @link TelegramBot
 */
public class CreateTablesCommands {
    private final Connection connection;

    // Constructor de la clase
    public CreateTablesCommands(Connection connection) {
        this.connection = connection;
    }

    // Cambiar la zona horaria de la base de datos
    public void setTimeZone(){
        String setTimeZoneSql = "SET GLOBAL time_zone = '-05:00';"; // Cambia el offset según tu zona horaria
        try (PreparedStatement setTimeZoneStmt = connection.prepareStatement(setTimeZoneSql)) {
            setTimeZoneStmt.executeUpdate();
            System.out.println("Zona horaria establecida correctamente");
        } catch (SQLException e) {
            System.out.println("Error al establecer la zona horaria");
        }
    }

    // Crear tablas
    public void createTablesCommand(){
        String createTablesString = """
            CREATE TABLE IF NOT EXISTS USERS (
            ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
            TELEGRAM_ID BIGINT NOT NULL,
            USERNAME VARCHAR(50) UNIQUE,
            CREATED_AT timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
            IS_PREMIUM BOOLEAN NOT NULL DEFAULT FALSE,
            EMAIL VARCHAR(50),
            HASHED_PWD VARCHAR(200),
            HASHED_SCTY_AWD VARCHAR(200),
            SALT_SCTY_SWR VARCHAR(50),
            PASSWORD_EXPIRY_DATE timestamp,
            ROLE ENUM('USER', 'ADMIN') NOT NULL DEFAULT 'USER',
            LAST_LOGIN TIMESTAMP NOT NULL,
            UNIQUE (TELEGRAM_ID)  
            );
        """;
        try(PreparedStatement createTablesStmt = connection.prepareStatement(createTablesString)){
            createTablesStmt.executeUpdate();
            System.out.println("Tablas creadas correctamente");
        } catch (SQLException e) {
            System.out.println("Error al crear las tablas");
            System.out.println(e);
        }
    }


    
}