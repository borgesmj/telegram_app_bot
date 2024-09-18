package com.saburo.telegrambot.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        } catch (Exception e) {
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
        } catch (Exception e) {
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
        } catch (Exception e) {
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
        } catch (Exception e) {
            System.out.println("Error al actualizar el ultimo login");
            System.out.println(e);
        }
    }
}
