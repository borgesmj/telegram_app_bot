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


    
}