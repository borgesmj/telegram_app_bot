package com.saburo.telegrambot.user;

import com.saburo.telegrambot.bot.TelegramBotContent;
import com.saburo.telegrambot.database.DatabaseCommands;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        double totalSavingsMovimientosTable = databaseCommands.getAmmountsByTypeOfMovement(userId, "AHORROS");
        double totalSavings = databaseCommands.getSavingsTotal(userId);
        // double getSavingsWoutInitial = databaseCommands.getSavingsWoutInitial();
        double newBalance = totalIncome - totalOutcome - totalSavingsMovimientosTable;
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

    public String getMovementById(int movementId) {
        String newReport = "";
        String movimiento = databaseCommands.getMovementById(movementId);
        String[] parts = movimiento.split("\\+");
        newReport = TelegramBotContent.USER_REPORT_3(parts[2], parts[0], parts[1], parts[3], parts[4]);
        return newReport;
    }

    public String getTotalByMonthCategoryType(long userId, int monthInt, String monthString) {
        int count = databaseCommands.checkForMovements(userId, monthInt);
        String newReport = "";
        if (count == 0) {
            newReport = String.format("""
                🤖 Hola! 👋

                Parece que no has registrado movimientos durante el mes de *%s*.
                
                ¡Recuerda que es importante llevar un buen control de tus finanzas! 😊

                """, monthString);
            return newReport;
        } else {

            double totalIncome = 0;
            double totalOutcome = 0;
            double balance = 0;
            newReport = String.format("""
                    🤖:
                    
                    ¡Hola! Aquí está tu reporte para el mes de *%s* 📊
                    
                    """, monthString);

            newReport += "💰 *Ingresos:*";
            List<String> categoriasIngresos = databaseCommands.getCategories(userId, "INGRESO");
            for (String categoria : categoriasIngresos) {
                Double ammount = databaseCommands.getTotalByMonthCategory(userId, categoria, monthInt);
                String ammountString = String.format("%.2f", ammount);
                totalIncome += ammount;
                newReport += "\n";
                newReport += "➡️ *" + categoria + ":* `" + ammountString + "`";
            }
            newReport += "\n\n";

            newReport += "💸 *Egresos:*";
            List<String> categoriasEgresos = databaseCommands.getCategories(userId, "EGRESO");
            for (String categoria : categoriasEgresos) {
                Double ammount = databaseCommands.getTotalByMonthCategory(userId, categoria, monthInt);
                String ammountString = String.format("%.2f", ammount);
                totalOutcome += ammount;
                newReport += "\n";
                newReport += "⬅️ *" + categoria + ":* `" + ammountString + "`";
            }
            newReport += "\n\n";

            newReport += "📊 *Resumen general:*\n";
            String totalIncomeString = String.format("%.2f", totalIncome);
            double savings = databaseCommands.getSavingsCurrentMonth(userId, monthInt);
            String savingsString = String.format("%.2f", savings);
            newReport += "\n";
            // AHORROS MES ACTUAL
            newReport += "🔒 *Ahorros este mes:* `" + savingsString + "`";
            // AHORROS TOTALES
            double totalSavings = databaseCommands.getTotalSavings(userId);
            double capitalInicial = databaseCommands.getCapitalInicial(userId);
            double initialSavings = databaseCommands.getInitialSavings(userId);
            String totalSavingsString = String.format("%.2f", totalSavings);
            newReport += "\n";
            newReport += "💰 *Total ahorros:* `" + totalSavingsString + "`";
            newReport += "\n\n";
            newReport += "🔵 *Total Ingresos:* `" + totalIncomeString + "`";
            newReport += "\n";
            String totalOutcomeString = String.format("%.2f", totalOutcome);
            newReport += "🔴 *Total Egresos:* `" + totalOutcomeString + "`";
            balance = capitalInicial + totalIncome - totalOutcome - savings + initialSavings;
            String balanceString = String.format("%.2f", balance);
            newReport += "\n";
            newReport += "⚖️ *Balance final:* `" + balanceString + "`";
            newReport += "\n\n";

            newReport += "¡Sigue así y mantén tus finanzas en equilibrio! 🚀";
            return newReport;
        }
    }

    public String getProfile(String username, long userId){
        String profileString ="";
        double totalIncome = databaseCommands.getAmmountsByTypeOfMovement(userId, "INGRESO");
        double totalOutcome = databaseCommands.getAmmountsByTypeOfMovement(userId, "EGRESO");
        double totalSavings = databaseCommands.getAmmountsByTypeOfMovement(userId, "AHORROS");
        double getSavingsWoutInitial = databaseCommands.getSavingsWoutInitial(userId);
        int numberOfMovements = databaseCommands.getMovementsCount(userId);
        double newBalance = totalIncome - totalOutcome - getSavingsWoutInitial;
        LocalDate lastDate = databaseCommands.getLastMovementDate(userId); // Cambiar a LocalDate si el método lo permite
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String lastDateString = lastDate.format(formatter);
        profileString = TelegramBotContent.USER_PROFILE(username, newBalance, numberOfMovements, lastDateString, totalSavings);
        return profileString;
    }

}
