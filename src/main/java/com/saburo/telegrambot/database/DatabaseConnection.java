package com.saburo.telegrambot.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.saburo.telegrambot.config.EnvConfig;

/**
 * Clase utilizada para conectar con la base de datos MySQL.
 * Para realizar la conexion a la base de datos, necesitamos de tres parámetros
 * 
 * @param args URL, usuario y contraseña.
 *             y llamamos al metodo getConnection() en @link TelegramBot para
 *             conectarnos a la base de datos.
 */
public class DatabaseConnection {

    public static final String URL = "jdbc:mysql://localhost:3306/REGISTRO_FINANZAS";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, EnvConfig.get("DATABASE_USERNAME"),
                    EnvConfig.get("DATABASE_PASSWORD"));
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el driver MySQL: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return connection;
    }
}