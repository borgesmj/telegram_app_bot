package com.saburo.telegrambot.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import com.saburo.telegrambot.bot.TelegramBot;

/**
 * DatabaseCommands
 */
public class DatabaseCommands {
    private final Connection connection;

    public DatabaseCommands(Connection connection) {
        this.connection = connection;
    }

    private Timestamp getCurrentTimestamp() {
        ZoneId zoneId = ZoneId.of("America/Bogota");  // Definir la zona horaria correcta
        LocalDateTime localDateTime = LocalDateTime.now(zoneId);  // Obtener la fecha y hora local
        return Timestamp.valueOf(localDateTime);  // Convertir LocalDateTime a Timestamp
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
        String SqlQueryString = "INSERT INTO USERS (TELEGRAM_ID, USERNAME, CREATED_AT, LAST_LOGIN, ROLE) VALUES (?, ?, ?, ?, ?)";
        String adminTelegramId = TelegramBot.getAdminChatId();
        String userTelegramIdString =  Long.toString(userTelegramId);
        try (PreparedStatement insertUserStmt = connection.prepareStatement(SqlQueryString)) {
            insertUserStmt.setLong(1, userTelegramId);
            insertUserStmt.setString(2, username.toLowerCase());
            insertUserStmt.setTimestamp(3, getCurrentTimestamp());
            insertUserStmt.setTimestamp(4, getCurrentTimestamp());
            if (adminTelegramId.equals(userTelegramIdString)){
                insertUserStmt.setString(5, "ADMIN");
            } else {
                insertUserStmt.setString(5, "USER");
            }
            insertUserStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al insertar el usuario");
            System.out.println(e);
            return "nombre duplicado";
        }
        return "";
    }

    /**
     * Metodo para obtener el nombre de usuario desde la base de datos
     * 
     * @param userId
     * @return
    *   
    */ 

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

    /**
     * Metodo para actualizar el ultimo login de un usuario
     * 
     * @param userId
     */

    public void updateLastLogin(long userId){
        String SqlQueryString = "UPDATE USERS SET LAST_LOGIN = ? WHERE TELEGRAM_ID = ?";
        try (PreparedStatement updateLastLoginStmt = connection.prepareStatement(SqlQueryString)){
            System.out.println(getCurrentTimestamp());
            updateLastLoginStmt.setTimestamp(1, getCurrentTimestamp());
            updateLastLoginStmt.setLong(2, userId);
            updateLastLoginStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar el ultimo login");
            System.out.println(e);
        }
    }

    /**
     * Metodo para obtener el id de un usuario en la base de datos desde el id de telegram
     * 
     * @param userId
     * @return
     */

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
                System.out.println("No se encontró un usuario con ese id.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener el ID del usuario: " + e.getMessage());
        }
        return currentUserId;
    }

    /**
     * Metodo para guardar el capital inicial de un usuario
     * 
     * @param userId
     * @param initialCapital
     */

    public void saveInitialCapital(long userId, double initialCapital){
        int currentUserId = getCurrentUserId(userId);
        String SqlQueryString = "INSERT INTO MOVIMIENTOS (DETALLES, MONTO, CREATED_AT, USER_ID, TIPO_MOVIMIENTO) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement saveInitialCapitalStmt = connection.prepareStatement(SqlQueryString)){
            saveInitialCapitalStmt.setString(1, "CAPITAL INICIAL");
            saveInitialCapitalStmt.setDouble(2, initialCapital);
            saveInitialCapitalStmt.setTimestamp(3, getCurrentTimestamp());
            saveInitialCapitalStmt.setInt(4, currentUserId);
            saveInitialCapitalStmt.setString(5, "INGRESO");
            saveInitialCapitalStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al crear el capital inicial");
            System.out.println(e);
        }
    }

    /**
     * Metodo para guardar los ahorros iniciales de un usuario
     * 
     * @param userId
     * @param initialSavings
     */

    public void saveIniatialSavings(long userId, double initialSavings){
        int currentUserId = getCurrentUserId(userId);
        String SqlQueryString = "INSERT INTO AHORROS (DETALLES, MONTO, CREATED_AT, USER_ID) VALUES (?, ?, ?, ?);";
        try (PreparedStatement saveInitialSavingsStmt = connection.prepareStatement(SqlQueryString)){
            saveInitialSavingsStmt.setString(1, "AHORRO INICIAL");
            saveInitialSavingsStmt.setDouble(2, initialSavings);
            saveInitialSavingsStmt.setTimestamp(3, getCurrentTimestamp());
            saveInitialSavingsStmt.setInt(4, currentUserId);
            saveInitialSavingsStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar el ultimo login");
            System.out.println(e);
        }
    }

    /**
     * Metodo para guardar las categorias de un usuario
     * 
     * @param userId
     * @param category
     * @param typeOfMovement
     */

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

    /**
     * Metodo para obtener el id de una categoria en la base de datos
     * 
     * @param category
     * @return
     */

    public int getCategoryId(String category) {
        String getUserIdStatement = "SELECT ID FROM CATEGORIAS WHERE NOMBRE = ?";
        int categoryId = -1; // Inicializamos userId con un valor por defecto

        try (PreparedStatement userIdStmt = connection.prepareStatement(getUserIdStatement)) {
            userIdStmt.setString(1, category);; // Asegúrate de que USERNAME sea una variable válida
            ResultSet rs = userIdStmt.executeQuery();

            // Verificamos si hay un resultado antes de llamar a rs.getInt
            if (rs.next()) {
                categoryId = rs.getInt(1); // Asigna el valor de ID a userId
            } else {
                System.out.println("No se encontró un usuario con esa categoria.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener el ID del usuario: " + e.getMessage());
        }
        return categoryId;
    }

    /**
     * Metodo para guardar un nuevo movimiento en la base de datos
     * 
     * @param userId
     * @param amount
     * @param details
     * @param category
     * @param typeOfMovement
     */

    public void saveNewMovement(long userId, double amount, String details, String category, String typeOfMovement){
        int currentUserId = getCurrentUserId(userId);
        int categoryId = getCategoryId(category);
        String SqlQueryString = "INSERT INTO MOVIMIENTOS (DETALLES, MONTO, CREATED_AT, USER_ID, CATEGORIA_ID, TIPO_MOVIMIENTO) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement saveNewMovementStmt = connection.prepareStatement(SqlQueryString)){
            saveNewMovementStmt.setString(1, details);
            saveNewMovementStmt.setDouble(2, amount);
            saveNewMovementStmt.setTimestamp(3, getCurrentTimestamp());
            saveNewMovementStmt.setInt(4, currentUserId);
            saveNewMovementStmt.setInt(5, categoryId);
            saveNewMovementStmt.setString(6, typeOfMovement);
            saveNewMovementStmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al ingresar nuevo movimiento");
            System.out.println(e);
        }

    }

    /**
     * Metodo para obtener las categorias de un usuario
     * 
     * @param userId
     * @param typeOfMovement
     * @return lista de categorias
     */

    public List<String> getCategories(long userId, String typeOfMovement) {
        List<String> categories = new ArrayList<>();
        int currentUserId = getCurrentUserId(userId);
        String insertUserSql = "SELECT NOMBRE FROM CATEGORIAS WHERE USER_ID = ? AND TIPO_MOVIMIENTO = ?";
        try (PreparedStatement insertUserStmt = connection.prepareStatement(insertUserSql)) {
            insertUserStmt.setInt(1, currentUserId);
            insertUserStmt.setString(2, typeOfMovement);
            ResultSet rs = insertUserStmt.executeQuery();
            while (rs.next()) {
                categories.add(rs.getString("NOMBRE"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener las categorias: " + e.getMessage());
        }
        return categories;
    }

    /**
     * Metodo para guardar un nuevo movimiento en la base de datos
     * 
     * @param userId
     * @param amount
     * @param details
     * @param category
     * @param typeOfMovement
     */

     public void savingsDeposit(long userId, double amount, String typeOfMovement){
        int currentUserId = getCurrentUserId(userId);
        String SqlSavingDepositMovimientosTable = "INSERT INTO MOVIMIENTOS (DETALLES, MONTO, CREATED_AT, USER_ID, TIPO_MOVIMIENTO) VALUES(?, ?, ?, ?, ?);";
        String SqlSavingDepositAhorrosTable = "INSERT INTO AHORROS (DETALLES, MONTO, CREATED_AT, USER_ID) VALUES ('DEPOSITO AHORROS', ?, ?, ?)";
        try (
            PreparedStatement saveNewMovementStmt = connection.prepareStatement(SqlSavingDepositMovimientosTable);
            PreparedStatement saveNewSavingsStmt = connection.prepareStatement(SqlSavingDepositAhorrosTable);
            ){
            saveNewMovementStmt.setString(1, "DEPOSITO AHORROS");
            saveNewMovementStmt.setDouble(2, amount);
            saveNewMovementStmt.setTimestamp(3, getCurrentTimestamp());
            saveNewMovementStmt.setInt(4, currentUserId);
            saveNewMovementStmt.setString(5, typeOfMovement);
            saveNewMovementStmt.executeUpdate();
            saveNewSavingsStmt.setDouble(1, amount);
            saveNewSavingsStmt.setTimestamp(2, getCurrentTimestamp());
            saveNewSavingsStmt.setInt(3, currentUserId);
            saveNewSavingsStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al ingresar nuevo ahorro");
            System.out.println(e);
        }

    }

    public double getAmmountsByTypeOfMovement(long userId, String typeOfMovement) {
        int currentUserId = getCurrentUserId(userId);  // Obtén el ID del usuario actual
        String SqlQueryString = "SELECT SUM(MONTO) FROM MOVIMIENTOS WHERE TIPO_MOVIMIENTO = ? AND USER_ID = ?";
        double amount = 0;
        
        try (PreparedStatement getAmmountsByCategoryStmt = connection.prepareStatement(SqlQueryString)) {
            getAmmountsByCategoryStmt.setString(1, typeOfMovement);  // Establecer el tipo de movimiento
            getAmmountsByCategoryStmt.setInt(2, currentUserId);       // Establecer el ID del usuario
            
            ResultSet rs = getAmmountsByCategoryStmt.executeQuery();   // Ejecutar la consulta y obtener el resultado
            
            if (rs.next()) {  // Verificar si hay resultados
                amount = rs.getDouble(1);  // Obtener el valor de la primera columna
            }
        } catch (SQLException e) {
            System.out.println("Error al generar monto");
            System.out.println(e);
        }
        
        return amount;  // Devolver el monto
    }

    public double getSavingsWoutInitial(long userId) {
        int currentUserId = getCurrentUserId(userId);
        String SqlQueryString = "SELECT SUM(MONTO) FROM MOVIMIENTOS WHERE USER_ID = ? AND TIPO_MOVIMIENTO = ? AND DETALLES != ?";
        double amount = 0;
        try (PreparedStatement getAmmountsByCategoryStmt = connection.prepareStatement(SqlQueryString)){
            getAmmountsByCategoryStmt.setInt(1, currentUserId);
            getAmmountsByCategoryStmt.setString(2, "AHORROS");
            getAmmountsByCategoryStmt.setString(3, "AHORRO INICIAL");
            ResultSet rs = getAmmountsByCategoryStmt.executeQuery();   // Ejecutar la consulta y obtener el resultado
            
            if (rs.next()) {  // Verificar si hay resultados
                amount = rs.getDouble(1);  // Obtener el valor de la primera columna
            }
        } catch (SQLException e) {
            System.out.println("Error al generar monto");
            System.out.println(e);
        }
        return amount;  // Devolver el monto
    }
    

    public List<String> getUltimosDiezMovimientos(long userId){
        List<String> movimientos = new ArrayList<>();
        int currentUserId = getCurrentUserId(userId);
        String SqlQueryString = """
            SELECT CONCAT(LPAD(ID, 3, '0'), "+", DETALLES, "+", MONTO, "+", TIPO_MOVIMIENTO) AS TRANSACCION FROM MOVIMIENTOS WHERE DETALLES != 'AHORRO INICIAL'  AND DETALLES != 'CAPITAL INICIAL'  AND USER_ID = ? ORDER BY CREATED_AT DESC LIMIT 10
                """;
        try (PreparedStatement getUltimosMovimientosStmt = connection.prepareStatement(SqlQueryString)){
            getUltimosMovimientosStmt.setInt(1, currentUserId);
            ResultSet rs = getUltimosMovimientosStmt.executeQuery();
            while (rs.next()) {
                movimientos.add(rs.getString("TRANSACCION"));
            }
        } catch (SQLException e) {
            System.out.println("Error al generar monto");
            System.out.println(e);
        }
        return movimientos;
    }

    public String getMovementById(int movementId){
        String SqlQueryString = "SELECT MOV.*, CAT.NOMBRE  FROM MOVIMIENTOS MOV LEFT JOIN CATEGORIAS CAT ON MOV.CATEGORIA_ID = CAT.ID WHERE MOV.ID = ?";
        try (PreparedStatement getMovementByIdStmt = connection.prepareStatement(SqlQueryString)){
            getMovementByIdStmt.setInt(1, movementId);
            ResultSet rs = getMovementByIdStmt.executeQuery();
            if (rs.next()) {
                String detalles = rs.getString("DETALLES");
                String monto = String.valueOf(rs.getDouble("MONTO"));
                String tipo = rs.getString("TIPO_MOVIMIENTO");
                String categoria = rs.getString("NOMBRE");
                String date = rs.getString("CREATED_AT");
                return detalles + "+" + monto + "+" + date + "+" + tipo + "+" + categoria;
            }
        } catch (SQLException e) {
            System.out.println("Error al generar monto");
            System.out.println(e);
        }
        return "";
    }

    public double getTotalByMonthCategory(long userId, String category, int monthInt){
        int currentUserId = getCurrentUserId(userId);
        int categoryId = getCategoryId(category);
        double result = 0;
        String SqlQueryString = "SELECT SUM(MONTO) FROM MOVIMIENTOS WHERE USER_ID = ? AND CATEGORIA_ID = ? AND MONTH(CREATED_AT) = ?;";
        try (PreparedStatement getTotalByMonthCategoryStmt = connection.prepareStatement(SqlQueryString)){
            getTotalByMonthCategoryStmt.setInt(1, currentUserId);
            getTotalByMonthCategoryStmt.setInt(2, categoryId);
            getTotalByMonthCategoryStmt.setInt(3, monthInt);
            ResultSet rs = getTotalByMonthCategoryStmt.executeQuery();
            if (rs.next()) {
                result = rs.getDouble(1);
            }

        } catch (SQLException e) {
            System.out.println("Error al generar monto");
            System.out.println(e);
        }
        return result;
    }

    public int checkForMovements(long userId, int monthInt){
        int currentUserId = getCurrentUserId(userId);
        String SqlQueryString = "SELECT COUNT(*) FROM MOVIMIENTOS WHERE MONTH(CREATED_AT) = ? AND USER_ID = ?";
        int count = 0;
        try (PreparedStatement getMovementsByMonthStmt = connection.prepareStatement(SqlQueryString)){
            getMovementsByMonthStmt.setInt(1, monthInt);
            getMovementsByMonthStmt.setInt(2, currentUserId);
            ResultSet rs = getMovementsByMonthStmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al generar monto");
            System.out.println(e);
        }
        return count;
    }

    public double getSavingsCurrentMonth(long userId, int monthInt){
        double result = 0;
        int currentUserId = getCurrentUserId(userId);
        String SqlQueryString = "SELECT SUM(MONTO) FROM AHORROS WHERE MONTH(CREATED_AT) = ? AND USER_ID = ? AND MONTO > 0;";
        try (PreparedStatement getSavingsCurrentMonthStmt = connection.prepareStatement(SqlQueryString)){
            getSavingsCurrentMonthStmt.setInt(1, monthInt);
            getSavingsCurrentMonthStmt.setInt(2, currentUserId);
            ResultSet rs = getSavingsCurrentMonthStmt.executeQuery();
            if (rs.next()) {
                result = rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al generar monto");
            System.out.println(e);
        }
        return result;
    }

    public double getSavingsWithdrawCurrentMonth(long userId, int monthInt){
        double result = 0;
        int currentUserId = getCurrentUserId(userId);
        String SqlQueryString = "SELECT SUM(MONTO) FROM AHORROS WHERE MONTH(CREATED_AT) = ? AND USER_ID = ? AND MONTO < 0;";
        try (PreparedStatement getSavingsCurrentMonthStmt = connection.prepareStatement(SqlQueryString)){
            getSavingsCurrentMonthStmt.setInt(1, monthInt);
            getSavingsCurrentMonthStmt.setInt(2, currentUserId);
            ResultSet rs = getSavingsCurrentMonthStmt.executeQuery();
            if (rs.next()) {
                result = rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al generar monto");
            System.out.println(e);
        }
        return result;
    }

    public double getTotalSavings(long userId){
        int currentUserId = getCurrentUserId(userId);
        double monto = 0;
        String SqlQueryString = "SELECT SUM(MONTO) FROM AHORROS WHERE USER_ID = ?";
        try (PreparedStatement getTotalSavingsStmt = connection.prepareStatement(SqlQueryString)){
            getTotalSavingsStmt.setInt(1, currentUserId);
            ResultSet rs = getTotalSavingsStmt.executeQuery();
            if (rs.next()) {
                monto = rs.getDouble(1);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al generar monto");
            System.out.println(e);
        }
        return monto;
    }

    public String updateUsername(long userId, String username){
        int currentUserId = getCurrentUserId(userId);

        String SqlQueryString = " UPDATE USERS SET USERNAME = ? WHERE ID = ?";
        try (PreparedStatement updateUsernameStmt = connection.prepareStatement(SqlQueryString)){
            updateUsernameStmt.setString(1, username);
            updateUsernameStmt.setInt(2, currentUserId);
            updateUsernameStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al generar monto");
            System.out.println(e);
            return "mensaje de error";
        }
        return "";
    }

    public Double getCapitalInicial(long userId){
        int currentUserId = getCurrentUserId(userId);
        String SqlQueryString = "SELECT MONTO FROM MOVIMIENTOS WHERE DETALLES = ? AND USER_ID = ?";
        Double monto = 0.0;
        try (PreparedStatement getCapitalInicialStmt = connection.prepareStatement(SqlQueryString)){
            getCapitalInicialStmt.setString(1, "CAPITAL INICIAL");
            getCapitalInicialStmt.setInt(2, currentUserId);
            ResultSet rs = getCapitalInicialStmt.executeQuery();
            if (rs.next()) {
                monto = rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al generar monto");
            System.out.println(e);
        }
        return monto;
    }

    public double getInitialSavings(long userId){
        int currentUserId = getCurrentUserId(userId);
        String SqlQueryString = "SELECT MONTO FROM MOVIMIENTOS WHERE DETALLES = ? AND USER_ID = ?";
        Double monto = 0.0;
        try (PreparedStatement getInitialSavingsStmt = connection.prepareStatement(SqlQueryString)){
            getInitialSavingsStmt.setString(1, "AHORRO INICIAL");
            getInitialSavingsStmt.setInt(2, currentUserId);
            ResultSet rs = getInitialSavingsStmt.executeQuery();
            if (rs.next()){
                monto = rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al generar monto");
            System.out.println(e);
        }
        return monto;
    }

    public int getMovementsCount(long userId){
        int currentUserId = getCurrentUserId(userId);
        String SqlQueryString = "SELECT COUNT(*) FROM MOVIMIENTOS WHERE USER_ID = ?";
        int count = 0;
        try (PreparedStatement getMovementsCountStmt = connection.prepareStatement(SqlQueryString)){
            getMovementsCountStmt.setInt(1, currentUserId);
            ResultSet rs = getMovementsCountStmt.executeQuery();
            if (rs.next()){
                count = rs.getInt(1);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al generar monto");
            System.out.println(e);
        }
        return count;
    }

    public LocalDate getLastMovementDate(long userId) {
        int currentUserId = getCurrentUserId(userId);
        String sqlQueryString = "SELECT CREATED_AT FROM MOVIMIENTOS WHERE USER_ID = ? ORDER BY CREATED_AT DESC LIMIT 1";
        LocalDate date = null;
        try (PreparedStatement getLastMovementDateStmt = connection.prepareStatement(sqlQueryString)) {
            getLastMovementDateStmt.setInt(1, currentUserId);
            ResultSet rs = getLastMovementDateStmt.executeQuery();
            if (rs.next()) {
                Date sqlDate = rs.getDate(1); // Obtén la fecha como java.sql.Date
                if (sqlDate != null) {
                    date = sqlDate.toLocalDate(); // Convierte java.sql.Date a java.time.LocalDate
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la última fecha de movimiento");
            System.out.println(e);
        }
        return date;
    }


    public int usersCount(){
        int count = 0;
        String SqlQueryString = "SELECT COUNT(*) FROM USERS";
        try {
            PreparedStatement usersCountStmt = connection.prepareStatement(SqlQueryString);
            ResultSet rs = usersCountStmt.executeQuery();
            if (rs.next()){
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al contar los usuarios");
            System.out.println(e);
        }
        return count;
    }

    public boolean getUserIsAdmin(long userId){
        int currentUserId = getCurrentUserId(userId);
        String SqlQueryString = "SELECT ROLE FROM USERS WHERE ID = ?";
        boolean isAdmin = false;
        try {
            PreparedStatement getUserIsAdminStmt = connection.prepareStatement(SqlQueryString);
            getUserIsAdminStmt.setInt(1, currentUserId);
            ResultSet rs = getUserIsAdminStmt.executeQuery();
            if (rs.next()){
                String role = rs.getString(1);
                if (role.equals("ADMIN")){
                    isAdmin = true;
                }
            }
        } catch (SQLException e) {
            System.out.println("no se puso encontrar role");
            System.out.println(e);
        }
        return isAdmin;
    }

    public double sumAllSavings(long userId){
        double ammount = 0;
        int currentUserId = getCurrentUserId(userId);
        String SqlQueryString = "SELECT SUM(MONTO) FROM AHORROS WHERE USER_ID = ?";
        try (PreparedStatement sumAllSavingsStmt = connection.prepareStatement(SqlQueryString)){
            sumAllSavingsStmt.setInt(1, currentUserId);
            ResultSet rs = sumAllSavingsStmt.executeQuery();
            if (rs.next()){
                ammount = rs.getDouble(1);
            }
        } catch (SQLException e){
            System.out.println("Error sumando ahorros");
            System.out.println(e);
        }
        return ammount;
    }

    public double getSavingsTotal(long userId){
        double ammount = 0;
        int currentUserId = getCurrentUserId(userId);
        String SqlQueryString = "SELECT SUM(MONTO) FROM AHORROS WHERE USER_ID = ?";
        try (PreparedStatement getSavingsAmmount = connection.prepareStatement(SqlQueryString)){
            getSavingsAmmount.setInt(1, currentUserId);
            ResultSet rs = getSavingsAmmount.executeQuery();
            if (rs.next()){
                ammount = rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println("Error sumando todos los ahorros");
        }
        return ammount;
    }

    public void savingsWithdraw(long userId, double ammount){
        int currentUserId = getCurrentUserId(userId);
        double negativeAmmount = ammount * -1;
        String SqlQueryString = "INSERT INTO AHORROS (DETALLES, MONTO, CREATED_AT, USER_ID) VALUES ('RETIRO AHORROS', ?, ?, ?)";
        try (PreparedStatement savingsWithdrawStmt = connection.prepareStatement(SqlQueryString)){
            savingsWithdrawStmt.setDouble(1, negativeAmmount);
            savingsWithdrawStmt.setTimestamp(2, getCurrentTimestamp());
            savingsWithdrawStmt.setInt(3, currentUserId);
            savingsWithdrawStmt.executeUpdate();
            
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("error manejado retiro deposito");
        }
    }
}   
