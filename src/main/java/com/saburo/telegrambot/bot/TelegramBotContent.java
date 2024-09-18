package com.saburo.telegrambot.bot;

import java.util.List;

/***
 * TelegramBotContent tiene los mensajes que se enviarán al usuario
 * Algunos son mensajes estaticos otros son metodos que retornan un String
 * 
 */
public class TelegramBotContent {

        /**
         * Menu principal
         * 
         */
        public static final String MENU_PRINCIPAL = """
                        *MENU PRINCIPAL*

                        1. /nuevoingreso - *Nuevo Ingreso*
                        2. /nuevoegreso - *Nuevo Egreso*
                        3. /nuevoahorro - *Nuevo Ahorro*
                        """;
        /**
         * Mensaje de bienvenida al usuario nuevo
         */
        public static final String USER_MSG_1 = """
                        🤖:

                        *HOLA*, veo que eres nuevo usuario

                        Configuremos tu perfil.
                            """;

        /**
         * Mensaje d ep´regunta si el usuario quiere dejar el nombre de usuario como lo
         * tiene en su perfil de telegram o cambiarlo
         */
        public static String USER_MSG_2(String username) {
                return String.format("""
                                🤖:

                                Veo que te llamas *%s*

                                ¿Quieres dejarlo asi? ¿O lo cambiamos?

                                /estabienasi

                                /cambiarlo
                                """, username);
        }

        /**
         * Si el usuario no tiene nombre de usaurio en telegram, el bot le solicita uno
         */
        public static final String USER_MSG_3 = """
                        🤖:

                        No tienes nombre de usuario, configuremos uno.

                        Escribe aqui abajo el nombre de usuario nuevo
                        """;
        /**
         * Si el usuario quiere cambiar el nombre de usuario
         * 
         */
        public static final String USER_MSG_4 = """
                        🤖:

                        Aqui debajo, escribe como quieres que te llame ahora.
                        """;
        /**
         * Si el nombre de usuario ya ha sido utilizado
         */
        public static final String USER_MSG_5 = """
                        🤖:

                        Disculpa, ese nombre de usuario ya ha sido utilizado. intenta con otro.
                                """;

        /**
         * Si el nombre de usuario es valido y fué guardado con exito
         */
        public static String USER_MSG_6(String username) {
                return String.format("""
                                🤖:

                                ✅ Excelente.

                                Tu nombre fué guardado con éxito, ahora te llamaré *%s*
                                """, username);
        }

        /**
         * Mensaje de bienvenida para cuando el usuario entre con el comando /start y ya
         * se encuentre guardado en la base de datos
         */
        public static String USER_MSG_7(String username) {
                return String.format("""
                                🤖:

                                Bienvenido de vuelta, *%s*
                                """, username);
        }

        public static final String USER_MSG_8 = """
                        🤖:

                        Ahora configuraremos tu capital inicial.

                        Escribe en un numero, sin puntos ni comas, el capital que tienes actualmente, o escribe solo un 0.
                        """;

        public static final String USER_MSG_9 = """

                        🤖:

                        ✅ Genial.

                        Ahora vamos a configurar tus ahorros. Escribe en un mensaje, sin comas ni puntos, el monto que quieras guardar en tus cuentas, o solo escribe 0.

                        Este paso es para mantener las cuentas en orden, ya que para los proximos ahorros que guardes, se restarán de tu capital.

                        """;
        public static final String USER_MSG_10 = """

                        🤖:

                        ✅ Excelente. Tus ahorros fueron guardados

                        Ahora configuraremos las categorias de tus movimientos.

                        Comencemos con las de los INGRESO.

                        Vas a escribir las categorias separadas por una coma.

                        Por ejemplo: salario, freelance, otros ingresos, etc.

                        """;
        public static final String USER_MSG_11 = """
                        🤖:

                        ✅ Que bien estamos trabajando tu y yo.

                        VAmos a configurar ahora las categorias de los egresos.

                        Igual que en el paso anterior, escribe en un mismo mensaje todas separadas por una coma.

                        Por ejemplo: comida, entretenimiento, renta, otros etc.
                        """;
        public static final String USER_MSG_12 = """
                        🤖:

                        🎉 GENIAL!!!

                        Tu perfil fue guardado exitosamente, ahora puedes empezar a registrar tus movimientos.

                        Puedes realizarlo desde el /menu principal
                        """;
        public static final String USER_MSG_13 = """
                        🤖:

                        *GUARDEMOS UN NUEVO INGRESO*

                        `Ingresa el monto`

                        Escribe en un mensaje, sin comas ni punto

                        Si quieres cancelar, haz click en /menu

                        """;
        public static final String USER_MSG_14 = """
                        🤖:

                        *GUARDEMOS UN NUEVO EGRESO*

                        `Ingresa el monto`

                        Escribe en un mensaje, sin comas ni punto

                        Si quieres cancelar, haz click en /menu

                        """;
        public static final String USER_MSG_15 = """
                        🤖:

                        Escribe en un texto, el `nombre de la transaccion`.

                        Si quieres cancelar, haz click en /menu
                        """;

        public static String USER_MSG_16(List<String> categorias) {
                String message = "🤖:\n\n";
                message += "ℹ️ Selecciona la categoria\n\n";
                for (String category : categorias) {
                        message += "/" + category + "\n";
                }
                message += "\nPara cancelar, haz click en /menu";
                return message; // mensaje a enviar
        }

        public static final String USER_MSG_17 = """
                        🤖:

                        No tienes categorias guardadas. Guarda tu primera categoria en /nuevacategoria
                        """;
        public static final String USER_MSG_18= """
                        🤖:

                        Movimiento guardado con exito!!!
                        """;
}
