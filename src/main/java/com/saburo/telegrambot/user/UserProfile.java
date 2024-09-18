package com.saburo.telegrambot.user;
/**
 * Esta clase representa el perfil de usuario en la aplicación de Telegram.
 * Incluye información como el ID de usuario de Telegram, nombre de usuario,
 * si es usuario premium, correo electrónico, contraseñas cifradas, rol,
 * y la fecha y hora del último inicio de sesión.
 */
public class UserProfile {

    // ID único del usuario en Telegram.
    private long telegramUserID;
    // Nombre de usuario asociado al perfil.
    private String username;
    // Metodo para cambiar los montos instroducidos por el usuario a numero
    private double doubleAmmount;
    /**
     * Estos parametros de abajo son para usarlos en un futuro
     */
    // Indica si el usuario es premium.
    // private boolean is_premium;
    // Correo electrónico del usuario.
    // private String email;
    // Contraseña cifrada del usuario.
    // private String HASHED_PWD;
    // Respuesta cifrada de seguridad del usuario (para recuperación de cuenta).
    // private String HASHED_SCTY_AWD;
    // Rol del usuario en la aplicación (por ejemplo, "USER", "ADMIN").
    // private String role;

    /**
     * Constructor que crea un perfil de usuario básico con un ID de Telegram y
     * nombre de usuario.
     * 
     * @param userId   ID único del usuario en Telegram.
     */
    public UserProfile() {
        this.telegramUserID = 0;
        this.username = "";
        this.doubleAmmount = 0;
        /**
         * 
         * this.is_premium = false; // Valor predeterminado: el usuario no es premium
         * this.email = ""; // Valor predeterminado: sin email
         * this.HASHED_PWD = ""; // Valor predeterminado: sin contraseña establecida
         * this.HASHED_SCTY_AWD = ""; // Valor predeterminado: sin respuesta de
         * seguridad
         * this.role = "USER"; // Valor predeterminado: rol de usuario estándar
         */
    }

    // Getters

    /**
     * Devuelve el ID de usuario de Telegram.
     * 
     * @return ID del usuario en Telegram.
     */
    public long getTelegramUserID() {
        return telegramUserID;
    }

    /**
     * Devuelve el nombre de usuario asociado a este perfil.
     * 
     * @return Nombre de usuario.
     */
    public String getUsername() {
        return username;
    }

    public double getAmmount(){
        return doubleAmmount;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setAmmount(String userText){
        try {
            doubleAmmount = Double.parseDouble(userText);
        } catch (NumberFormatException e) {
            System.out.println("Error al convertir el texto a double");
        }
    }


}
