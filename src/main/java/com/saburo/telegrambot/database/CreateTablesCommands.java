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
    // Crear tablas
    public void createTablesCommand() {
        // Crear la tabla USERS
        String createUsersTable = """
                    CREATE TABLE IF NOT EXISTS USERS (
                    ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                    TELEGRAM_ID BIGINT NOT NULL,
                    USERNAME VARCHAR(50) UNIQUE,
                    CREATED_AT timestamp NOT NULL,
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

        // Crear la tabla MOVIMIENTOS
        String createMovimientosTable = """
                    CREATE TABLE IF NOT EXISTS MOVIMIENTOS (
                    ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                    DETALLES VARCHAR(250) NOT NULL DEFAULT 'CAPITAL INICIAL',
                    MONTO DOUBLE NOT NULL,
                    CREATED_AT TIMESTAMP,
                    USER_ID INT NOT NULL,
                    CATEGORIA_ID INT,
                    TIPO_MOVIMIENTO ENUM('INGRESO', 'EGRESO', 'AHORROS'),
                    FOREIGN KEY (USER_ID) REFERENCES USERS(ID),
                    FOREIGN KEY (CATEGORIA_ID) REFERENCES CATEGORIAS(ID)
                    );
                """;
        // Crear la tabla CATEGORIAS
        String createCategoriasTable = """
                    CREATE TABLE IF NOT EXISTS CATEGORIAS (
                    ID INT PRIMARY KEY AUTO_INCREMENT,
                    NOMBRE VARCHAR(50) NOT NULL,
                    USER_ID INT NOT NULL,
                    TIPO_MOVIMIENTO ENUM('INGRESO', 'EGRESO'),
                    CONSTRAINT unique_categoria_usuario UNIQUE (NOMBRE, USER_ID),
                    FOREIGN KEY (USER_ID) REFERENCES USERS(ID)
                    );
                """;
        // Crear la tablña de gastos fijos
        String createGastosFijosTable = """
                    CREATE TABLE IF NOT EXISTS GASTOS_FIJOS (
                    ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                    DETALLES VARCHAR(50),
                    MONTO DOUBLE NOT NULL,
                    CREATED_AT TIMESTAMP NOT NULL,
                    DIA_DEL_MES INT,
                    CATEGORIA_ID INT NOT NULL,
                    USER_ID INT NOT NULL,
                    FOREIGN KEY (USER_ID) REFERENCES USERS(ID),
                    FOREIGN KEY (CATEGORIA_ID) REFERENCES CATEGORIAS(ID),
                    UNIQUE (USER_ID, DETALLES)
                    );
                """;
        try (
                // Preparar las sentencias de creación de tablas
                PreparedStatement createUsersStmt = connection.prepareStatement(createUsersTable);
                PreparedStatement createCategoriasStmt = connection.prepareStatement(createCategoriasTable);
                PreparedStatement createMovimientosStmt = connection.prepareStatement(createMovimientosTable);
                PreparedStatement createGastosFijosStmt = connection.prepareStatement(createGastosFijosTable);
            ) {
            // Ejecutar las sentencias de creación de tablas una por una
            createUsersStmt.executeUpdate();
            createCategoriasStmt.executeUpdate();
            createMovimientosStmt.executeUpdate();
            System.out.println("Tablas creadas correctamente");
        } catch (SQLException e) {
            System.out.println("Error al crear las tablas");
            System.out.println(e);
        }
    }
}
