package com.saburo.telegrambot.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * DatabaseCommands
 */
public class DatabaseCommands {
    private final Connection connection;

    public DatabaseCommands(Connection connection) {
        this.connection = connection;
    }

    /****
     * checkUserId
     * metodo para verificar si el usuario existe en la base de datos
     * 
     * @param userId
     * @return true o false
     */
    public boolean checkUserId(long userId) {
        String SqlQueryString = "SELECT COUNT(*) FROM USERS WHERE TELEGRAM_ID = ?";
        try (PreparedStatement checkUserIdStmt = connection.prepareStatement(SqlQueryString)) {
            checkUserIdStmt.setLong(1, userId);
            ResultSet rs = checkUserIdStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            if (count < 1) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar el usuario");
            System.out.println(e);
        }
        return false;
    }
    /****
     * insertNewUser
     * metodo para insertar un nuevo usuario en la base de datos
     * 
     * @param userTelegramId
     * @param username
     */
    public String insertNewUser(long userTelegramId, String username) {
        String SqlQueryString = "INSERT INTO USERS (TELEGRAM_ID, USERNAME, LAST_LOGIN) VALUES (?, ?, ?)";
        try (PreparedStatement insertUserStmt = connection.prepareStatement(SqlQueryString)) {
            insertUserStmt.setLong(1, userTelegramId);
            insertUserStmt.setString(2, username.toLowerCase());
            ZoneId zoneId = ZoneId.of("America/Bogota");
            LocalDateTime localDateTime = LocalDateTime.now(zoneId);
            insertUserStmt.setTimestamp(3, Timestamp.valueOf(localDateTime));
            insertUserStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al insertar el usuario");
            System.out.println(e);
            return "nombre duplicado";
        }
        return "";
    }

    public String getCurrentUsername(long userId){
        String SqlQueryString = "SELECT USERNAME FROM USERS WHERE TELEGRAM_ID = ?";
        try (PreparedStatement getCurrentUsernameStmt = connection.prepareStatement(SqlQueryString)) {
            getCurrentUsernameStmt.setLong(1, userId);
            ResultSet rs = getCurrentUsernameStmt.executeQuery();
            rs.next();
            return rs.getString("USERNAME");
        } catch (SQLException e) {
            System.out.println("Error al obtener el nombre de usuario");
            System.out.println(e);
        }
        return "";
    }

    public void updateLastLogin(long userId){
        String SqlQueryString = "UPDATE USERS SET LAST_LOGIN = ? WHERE TELEGRAM_ID = ?";
        try (PreparedStatement updateLastLoginStmt = connection.prepareStatement(SqlQueryString)){
            updateLastLoginStmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            updateLastLoginStmt.setLong(2, userId);
            updateLastLoginStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar el ultimo login");
            System.out.println(e);
        }
    }

    public int getCurrentUserId(long userId) {
        String getUserIdStatement = "SELECT ID FROM USERS WHERE TELEGRAM_ID = ?";
        int currentUserId = -1; // Inicializamos userId con un valor por defecto

        try (PreparedStatement userIdStmt = connection.prepareStatement(getUserIdStatement)) {
            userIdStmt.setLong(1, userId); // Asegúrate de que USERNAME sea una variable válida
            ResultSet rs = userIdStmt.executeQuery();

            // Verificamos si hay un resultado antes de llamar a rs.getInt
            if (rs.next()) {
                currentUserId = rs.getInt(1); // Asigna el valor de ID a userId
            } else {
                System.out.println("No se encontró un usuario con ese nombre.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener el ID del usuario: " + e.getMessage());
        }
        return currentUserId;
    }

    public void saveInitialCapital(long userId, double initialCapital){
        int currentUserId = getCurrentUserId(userId);
        String SqlQueryString = "INSERT INTO MOVIMIENTOS (DETALLES, MONTO, USER_ID, TIPO_MOVIMIENTO) VALUES (?, ?, ?, ?)";
        try (PreparedStatement saveInitialCapitalStmt = connection.prepareStatement(SqlQueryString)){
            saveInitialCapitalStmt.setString(1, "CAPITAL INICIAL");
            saveInitialCapitalStmt.setDouble(2, initialCapital);
            saveInitialCapitalStmt.setInt(3, currentUserId);
            saveInitialCapitalStmt.setString(4, "INGRESO");
            saveInitialCapitalStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al crear el capital inicial");
            System.out.println(e);
        }
    }

    public void saveIniatialSavings(long userId, double initialSavings){
        int currentUserId = getCurrentUserId(userId);
        String SqlQueryString = "INSERT INTO MOVIMIENTOS (DETALLES, MONTO, USER_ID, TIPO_MOVIMIENTO) VALUES (?, ?, ?, ?)";
        try (PreparedStatement saveInitialSavingsStmt = connection.prepareStatement(SqlQueryString)){
            saveInitialSavingsStmt.setString(1, "AHORRO INICIAL");
            saveInitialSavingsStmt.setDouble(2, initialSavings);
            saveInitialSavingsStmt.setInt(3, currentUserId);
            saveInitialSavingsStmt.setString(4, "AHORROS");
            saveInitialSavingsStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar el ultimo login");
            System.out.println(e);
        }
    }

    public void saveCategories(long userId, String category, String typeOfMovement){
        int currentUserId = getCurrentUserId(userId);
        String SqlQueryString = " INSERT INTO CATEGORIAS (NOMBRE, USER_ID, TIPO_MOVIMIENTO) VALUES (?, ?, ?)";
        try (PreparedStatement saveCategoriesStmt = connection.prepareStatement(SqlQueryString)){
            saveCategoriesStmt.setString(1, category);
            saveCategoriesStmt.setInt(2, currentUserId);
            saveCategoriesStmt.setString(3, typeOfMovement);
            saveCategoriesStmt.execute();
        } catch (SQLException e) {
            System.out.println("Error al ingresar categoria");
            System.out.println(e);
        }
    }

    public int getCAtegoryId(String category) {
        String getUserIdStatement = "SELECT ID FROM CATEGORIAS WHERE NOMBRE = ?";
        int categoryId = -1; // Inicializamos userId con un valor por defecto

        try (PreparedStatement userIdStmt = connection.prepareStatement(getUserIdStatement)) {
            userIdStmt.setString(1, category);; // Asegúrate de que USERNAME sea una variable válida
            ResultSet rs = userIdStmt.executeQuery();

            // Verificamos si hay un resultado antes de llamar a rs.getInt
            if (rs.next()) {
                categoryId = rs.getInt(1); // Asigna el valor de ID a userId
            } else {
                System.out.println("No se encontró un usuario con ese nombre.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener el ID del usuario: " + e.getMessage());
        }
        return categoryId;
    }

    public void saveNewMovement(long userId, double amount, String details, String category, String typeOfMovement){
        int currentUserId = getCurrentUserId(userId);
        int categoryId = getCAtegoryId(category);
        String SqlQueryString = "INSERT INTO MOVIMIENTOS (DETALLES, MONTO, USER_ID, CATEGORIA_ID, TIPO_MOVIMIENTO) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement saveNewMovementStmt = connection.prepareStatement(SqlQueryString)){
            saveNewMovementStmt.setString(1, details);
            saveNewMovementStmt.setDouble(2, amount);
            saveNewMovementStmt.setInt(3, currentUserId);
            saveNewMovementStmt.setInt(4, categoryId);
            saveNewMovementStmt.setString(5, typeOfMovement);
            saveNewMovementStmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al ingresar nuevo movimiento");
            System.out.println(e);
        }

    }
}
