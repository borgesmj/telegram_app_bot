package com.saburo.telegrambot.user;

import com.saburo.telegrambot.bot.TelegramBotContent;
import com.saburo.telegrambot.database.DatabaseCommands;

import java.sql.Connection;
import java.util.List;

/**
 * UserReports
 * son los distintos reportes solciitados por el usuario
 * 
 */
public class UserReports {
    private final DatabaseCommands databaseCommands;

    public UserReports(Connection connection) {
        this.databaseCommands = new DatabaseCommands(connection);
    }

    /**
     * getBalanceGeneral nos regresa el balance de saldos que tiene el usuario
     * 
     * @args userId es el id del usuario que se quiere obtener el balance
     * @code totalIncome es la suma de todos los ingresos que tiene el usuario
     *       @code totalOutcome es la suma de todos los egresos que tiene el usuario
     * @code totalSavings es la suma de todos los ahorros que tiene el usuario
     *       @code getSavingsWoutInitial es la suma de todos los ahorros que tiene
     *       el usuario sin el primer ahorro, el ahorro inicial que se configura
     *       cuando el usuario se registra en la app
     * @code newBalance es la suma de todos los ingresos menos los egresos menos los
     *       ahorros
     *       @return newReport es el reporte reado en @link TelegramBotContent y que
     *       se va a enviar al usuario en la clase @link CommandHandler
     */
    public String getBalanceGeneral(long userId) {
        double totalIncome = databaseCommands.getAmmountsByTypeOfMovement(userId, "INGRESO");
        double totalOutcome = databaseCommands.getAmmountsByTypeOfMovement(userId, "EGRESO");
        double totalSavings = databaseCommands.getAmmountsByTypeOfMovement(userId, "AHORROS");
        double getSavingsWoutInitial = databaseCommands.getSavingsWoutInitial(userId);
        double newBalance = totalIncome - totalOutcome - getSavingsWoutInitial;
        // Convertir los valores double a String
        String incomeString = String.format("%.2f", totalIncome);
        String outcomeString = String.format("%.2f", totalOutcome);
        String savingsString = String.format("%.2f", totalSavings);
        String balanceString = String.format("%.2f", newBalance);
        // Llama al método estático directamente desde la clase TelegramBotContent
        String newReport = TelegramBotContent.USER_REPORT_1(incomeString, outcomeString, savingsString, balanceString);
        // se envia el String a commandHandler para que envie como mensaje al usuario
        return newReport;
    }
    

    public String getUltimosMovimientos(long userId) {
        String newReport = "";
        List<String> movimientos = databaseCommands.getUltimosDiezMovimientos(userId);
        newReport += TelegramBotContent.USER_REPORT_2(movimientos);
        return newReport;
    }

    public String getMovementById(int movementId){
        String newReport = "";
        String movimiento = databaseCommands.getMovementById(movementId);
        String[] parts = movimiento.split("\\+");
        newReport = TelegramBotContent.USER_REPORT_3(parts[2], parts[0], parts[1], parts[3], parts[4]);
        return newReport;
    }
}
