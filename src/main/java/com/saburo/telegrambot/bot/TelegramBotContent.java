package com.saburo.telegrambot.bot;

/***
 * TelegramBotContent tiene los mensajes que se enviarán al usuario
 * Algunos son mensajes estaticos otros son metodos que retornan un String
 * 
 */
public class TelegramBotContent {
        /**
         * Mensaje de bienvenida al usuario nuevo
         */
        public static final String USER_MSG_1 = """
                        🤖:

                        *HOLA*, veo que eres nuevo usuario

                        Configuremos tu perfil.
                            """;

        /**
         * Mensaje d ep´regunta si el usuario quiere dejar el nombre de usuario como lo tiene en su perfil de telegram o cambiarlo
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

                                Tu nombre fué guardado con éxito, ahora te llamaré *%s*
                                """, username);
        }
        /**
         * Mensaje de bienvenida para cuando el usuario entre con el comando /start y ya se encuentre guardado en la base de datos
         */
        public static String USER_MSG_7(String username) {
                return String.format("""
                                🤖:

                                Bienvenido de vuelta, *%s*
                                """, username);
        }

}
