package com.saburo.telegrambot.bot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/***
 * TelegramBotContent tiene los mensajes que se enviarán al usuario
 * Algunos son mensajes estaticos otros son metodos que retornan un String
 * 
 */
public class TelegramBotContent {

        public static final String MENU_PRINCIPAL = """
                        *✨ MENU PRINCIPAL ✨*

                        1. /nuevoingreso - *💵 Nuevo Ingreso*
                        2. /nuevoegreso - *🧾 Nuevo Egreso*
                        3. /nuevoahorro - *🏦 Nuevo Ahorro*
                        4. /reportes - *📊 Reportes*
                        5. /perfil - *👤 Perfil*
                        6. /about - *❓ Acerca de*
                        7. /donar - *💰 Apoya el proyecto*
                        """;

        public static final String SUB_MENU_REPORTES = """
                        *📊 MENU DE REPORTES 📊*

                        1. /balancegeneral - *📈 Balance General*
                        2. /ultimosmovimientos - *📜 Últimos Movimientos*
                        3. /reportemensual - *📅 Reporte Mensual*
                        4. /reporteahorros - *🏦 Reporte de Ahorros*
                        5. /menu - *🚪 Regresar al menú principal*
                        """;

        public static final String SUB_MENU_MESES = """
                        🤖:
                        *Por favor selecciona el mes*


                        1. /enero
                        2. /febrero
                        3. /marzo
                        4. /abril
                        5. /mayo
                        6. /junio
                        7. /julio
                        8. /agosto
                        9. /septiembre
                        10. /octubre
                        11. /noviembre
                        12. /diciembre
                        """;
        public static final String SUB_MENU_EDITAR_PERFIL = """
                        🤖:
                        *Por favor selecciona una opcion*


                        1. /editarnombre
                        2. /editarcategorias
                        3. /regresar
                        """;

        public static final String SUB_MENU_EDITAR_CATEGORIAS = """
                        🤖:
                        *Por favor selecciona una opcion*

                        1. /agregarnueva
                        2. /vermiscategorias
                        4. /menu
                        """;

        public static final String SUB_MENU_INGRESO_EGRESO = """
                        🤖:
                        *Por favor selecciona una opcion*

                        1. /Ingreso
                        2. /Egreso
                        3. /menu

                                """;
        public static final String MENU_ADMIN = """
                        🤖:
                        *MENU ADMIN*

                        1. /verusuarios
                        2. /conteousuarios
                        """;
        public static final String USER_MSG_1 = """
                        🤖 ¡Hola! 👋

                        *Bienvenido, nuevo usuario* 🎉

                        Configuremos tu perfil. 🛠️
                        """;

        public static String USER_MSG_2(String username) {
                return String.format("""
                                🤖 Veo que te llamas *%s* 😊

                                ¿Quieres dejarlo así o prefieres cambiarlo? 🤔

                                /estabienasi - *Sí, está bien así* 👍
                                /cambiarlo - *Quiero cambiarlo* 🔄
                                """, username);
        }

        public static final String USER_MSG_3 = """
                        🤖 No tienes nombre de usuario. 🆕

                        ¡Vamos a configurar uno! 📝

                        Escribe aquí abajo tu nuevo nombre de usuario:
                        """;

        public static final String USER_MSG_4 = """
                        🤖 Escribe cómo te gustaría que te llame. ✍️
                        """;

        public static final String USER_MSG_5 = """
                        🤖 Lo siento, ese nombre de usuario ya está en uso. 😔

                        Intenta con otro, ¡seguro encontramos uno perfecto! 🌟
                        """;

        public static String USER_MSG_6(String username) {
                return String.format("""
                                🤖 ¡Excelente! ✅

                                Tu nombre fue guardado con éxito, ahora te llamaré *%s* 🎊
                                """, username);
        }

        public static String USER_MSG_7(String username) {
                return String.format("""
                                🤖 Bienvenido de vuelta, *%s*! 🎉

                                ¡Estamos listos para comenzar! 🚀
                                """, username);
        }

        public static final String USER_MSG_8 = """
                        🤖 Ahora configuraremos tu capital inicial. 💰

                        Escribe un número, sin puntos ni comas. Si es 0, ¡también está bien! 😊
                        """;

        public static final String USER_MSG_9 = """
                        🤖 ¡Genial! ✅

                        Ahora, configura tus ahorros. 💵

                        Escribe el monto que deseas guardar, o solo 0 si prefieres. 😊
                        """;

        public static final String USER_MSG_10 = """
                        🤖 ¡Excelente! 🙌

                        Ahora configuraremos las categorías de tus movimientos. 🏷️

                        Comencemos con las de los *INGRESOS*. 💵

                        Escribe las categorías separadas por comas, por ejemplo: salario, freelance, otros ingresos, etc.
                        """;

        public static final String USER_MSG_11 = """
                        🤖 ¡Qué bien estamos trabajando juntos! 🤝

                        Vamos a configurar ahora las categorías de los *EGRESOS*. 🧾

                        Igual que antes, escribe todas separadas por comas. 😊
                        """;

        public static final String USER_MSG_12 = """
                        🤖 🎉 ¡GENIAL!

                        Tu perfil fue guardado exitosamente. Ahora puedes empezar a registrar tus movimientos. 📝

                        Puedes hacerlo desde el /menu principal. 📋
                        """;

        public static final String USER_MSG_13 = """
                        🤖 *GUARDEMOS UN NUEVO INGRESO* 💵

                        `Ingresa el monto` 💰

                        Escribe sin comas ni puntos.

                        Si quieres cancelar, haz clic en /menu 🚪
                        """;

        public static final String USER_MSG_14 = """
                        🤖 *GUARDEMOS UN NUEVO EGRESO* 🧾

                        `Ingresa el monto` 💸

                        Escribe sin comas ni puntos.

                        Si quieres cancelar, haz clic en /menu 🚪
                        """;

        public static final String USER_MSG_15 = """
                        🤖 Escribe el `nombre de la transacción`. ✍️

                        Si quieres cancelar, haz clic en /menu 🚪
                        """;

        public static String USER_MSG_16(List<String> categorias) {
                StringBuilder message = new StringBuilder("🤖\n\nℹ️ Selecciona la categoría:\n\n");
                for (String category : categorias) {
                        message.append("/").append(category).append("\n");
                }
                message.append("\nPara cancelar, haz clic en /menu 🚪");
                return message.toString();
        }

        public static final String USER_MSG_17 = """
                        🤖 No tienes categorías guardadas. 🗂️

                        Guarda tu primera categoría en /nuevacategoria ✨
                        """;

        public static final String USER_MSG_18 = """
                        🤖 ¡Movimiento guardado con éxito! 🎉
                        """;

        public static final String USER_MSG_19 = """
                        🤖 *GUARDEMOS UN NUEVO AHORRO* 🏦

                        `Ingresa el monto` 💰

                        Escribe sin comas. Si necesitas decimales, usa un punto. Ej: 120350.30

                        Si quieres cancelar, haz clic en /menu 🚪
                        """;

        public static final String USER_MSG_20 = """
                        🤖 ¡Movimiento guardado con éxito! 🎉
                        """;

        public static final String USER_MSG_21 = """
                        Presiona /menu para regresar al menú principal. 📋
                        """;

        public static final String USER_MSG_22 = """
                        Para regresar a la lista de movimientos, presiona /ultimosmovimientos. 📜
                        """;
        public static final String USER_MSG_23 = """
                        O presiona /reportemensual para volver al listado de los meses.
                        """;
        public static final String USER_MSG_24 = """
                        🤖 

                        Llegamos a la meta que querias!!!

                        Dime, ¿cuanto quieres retirar?

                        o si aun no lo quieres hacer, presiona /menu
                        """;

        public static String USER_REPORT_1(String ingresos, String egresos, String ahorros, String balance) {
                return String.format("""
                                🤖 *Reporte General* 📊

                                Este es tu reporte general hasta la fecha:

                                Ingresos: `%s` 💵
                                Egresos: `%s` 🧾

                                Balance: `%s` ⚖️

                                Ahorros: `%s` 🏦
                                """, ingresos, egresos, balance, ahorros);
        }

        public static String USER_REPORT_2(List<String> movimientos) {
                StringBuilder report = new StringBuilder("""
                                🤖 *Aquí tienes tus últimos 10 movimientos* 📜

                                """);
                for (int i = 0; i < movimientos.size(); i++) {
                        String movimiento = movimientos.get(i);
                        String[] parts = movimiento.split("\\+");
                        report.append("/ver");
                        report.append(parts[0]);
                        report.append(" ");
                        String emoji = parts[3].equals("INGRESO") ? "⬆️" : "⬇️";
                        report.append(emoji).append(" ");
                        report.append(parts[1]);
                        report.append(" ");
                        report.append("\n");
                }

                return report.toString();
        }

        public static String USER_REPORT_4(double savingsAmmount){
                return String.format("""
                                🤖:

                                Hasta la fecha, tienes un ahorro de: `%.2f` 🏦

                                Si quieres realizar un retiro de ahorros, haz clic en /retiroahorro.
                                """, savingsAmmount);
        }

        public static String USER_REPORT_3(String stringDate, String stringDetails, String ammount,
                        String typeOfMovement, String category) {
                try {
                        SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = sdfSource.parse(stringDate);
                        SimpleDateFormat sdfDestination = new SimpleDateFormat("dd/MM/yyyy");
                        stringDate = sdfDestination.format(date);
                } catch (ParseException e) {
                        System.out.println("Parse Exception: " + e);
                }
                return String.format("""
                                🤖:

                                Movimiento del día *%s* 🗓️

                                *Detalles: %s* ✨
                                Monto: `%s` 💰

                                Tipo: %s
                                Bajo la categoría: %s 🏷️
                                """, stringDate, stringDetails, ammount, typeOfMovement, category);
        }

        public static String categoriesList(String[] incomeCategories, String[] outcomeCategories) {
                String listMesage = "";
                listMesage += """
                                🤖:

                                Esta es tu lista de categorias:

                                💰 *Ingresos*:
                                """;
                for (String incomeCategory : incomeCategories) {
                        listMesage += incomeCategory + "\n";
                }
                listMesage += """

                                🧾 *Egresos*:
                                """;
                for (String outcomeCategory : outcomeCategories) {
                        listMesage += outcomeCategory + "\n";
                }
                return listMesage;
        }

        public static String USER_PROFILE(String name, double balance, int totalMovements, String lastActivityDate,
                        double ahorros) {
                return String.format("""
                                🤖 *Perfil del Usuario*

                                👤 Nombre: %s
                                💼 Saldo actual: `%.2f`
                                💰 Ahorros: `%.2f`
                                📊 Total de movimientos: %d
                                🕒 Última actividad: %s

                                ¡Sigue gestionando tus finanzas con éxito!

                                Si quieres cambiar tu perfil, haz click en /editarperfil
                                """, name, balance, ahorros, totalMovements, lastActivityDate);
        }

        public static final String ERROR_MESSAGE = """
                        🤖 Tienes un error en tu monto. 🚫

                        Por favor incluye solo números. 📝

                        Si necesitas que tenga decimales, escríbelo con un punto. Ejemplo: 120000.30
                        """;
        public static final String DONATE_MESSAGE = """
                        🤖 ¡Gracias por querer apoyar el proyecto!

                        Con tu donacion nos ayudas a crecer este proyecto y crear muchos mas.

                        Para donar puedes entrar al siguiente [enlace](https://buymeacoffee.com/borgesmj19)


                        """;

        public static final String ABOUT_MESSAGE = """
                        🤖:

                        ¿Sobre quien quieres saber?

                        1. /estebot - *Acerca de este bot*
                        2. /micreador - *Acerca de mi creador*
                        3. /menu - *Regresar al menu principal*


                        """;
        public static final String THIS_BOT_MESSAGE = """
                        🤖:
                        ¡Hola! Soy Finance Bot, y estoy aquí para ayudarte a mantener tus cuentas personales al día.
                        ¡Porque siempre es importante tener el control de tus finanzas! 💸
                        Recuerda mantener al dia tus cuentas.
                        """;

        public static final String CREATOR_MESSAGE ="""
                        🤖:
                        Mi programador es Miguel, un desarrollador frontend, con experiencia en web apps.

                        Puedes ver su trabajo en su [portfolio](https://borgesmj.github.io/) o su perfil de [github](https://github.com/borgesmj19)
                        """;
}
