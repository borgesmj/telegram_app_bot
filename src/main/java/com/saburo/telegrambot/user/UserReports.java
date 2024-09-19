package com.saburo.telegrambot.user;

import com.saburo.telegrambot.bot.TelegramBotContent;
import com.saburo.telegrambot.database.DatabaseCommands;

import java.sql.Connection;

/**
 * UserReports
 */
public class UserReports {
    private final DatabaseCommands databaseCommands;

    public UserReports(Connection connection) {
        this.databaseCommands = new DatabaseCommands(connection);
    }

    public String getBalanceGeneral(long userId) {
        // Lógica para obtener el balance general del usuario
        double totalIncome = databaseCommands.getAmmountsByTypeOfMovement(userId, "INGRESO");
        double totalOutcome = databaseCommands.getAmmountsByTypeOfMovement(userId, "EGRESO");
        double totalSavings = databaseCommands.getAmmountsByTypeOfMovement(userId, "AHORROS");
        double totalIncomeWOutInitialSavins = databaseCommands.getSavingsWoutInitial(userId);
        double newBalance = totalIncome - totalOutcome - totalIncomeWOutInitialSavins;
        // Convertir los valores double a String
        String incomeString = String.format("%.2f", totalIncome);
        String outcomeString = String.format("%.2f", totalOutcome);
        String savingsString = String.format("%.2f", totalSavings);
        String balanceString = String.format("%.2f", newBalance);
        System.out.println(incomeString + outcomeString + savingsString + balanceString);
        // Llama al método estático directamente desde la clase
        String newReport = TelegramBotContent.USER_REPORT_1(incomeString, outcomeString, savingsString, balanceString);
        return newReport;
    }
}
