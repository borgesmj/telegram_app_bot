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
    // Detalles de cada movimientos
    private String movementDetails;
    // Categoria del movimiento
    private String category;
    // Mes del año
    private int monthInt;
    // isnewuser
    private boolean isNewUser;
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
     * @param userId ID único del usuario en Telegram.
     */
    public UserProfile() {
        this.telegramUserID = 0;
        this.username = "";
        this.doubleAmmount = 0;
        this.movementDetails = "";
        this.category = "";
        this.monthInt = 0;
        this.isNewUser = false;
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

    public double getAmmount() {
        return doubleAmmount;
    }

    public String getMovementDetails() {
        return movementDetails;
    }

    public String getCategory() {
        return category;
    }

    public int getMonth() {
        return monthInt;
    }

    public boolean getIsNewUser(){
        return isNewUser;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public boolean setAmmount(String userText) {
        try {
            doubleAmmount = Double.parseDouble(userText);
        } catch (NumberFormatException e) {
            System.out.println("Error al convertir el texto a double");
            return false;
        }
        return true;
    }

    public void setMovementDetails(String userText) {
        movementDetails = userText;
    }

    public void setCategory(String userText) {
        category = userText;
    }

    public void setMonth(String monthString) {
        switch (monthString) {
            case "enero":
                monthInt = 1;
                break;
            case "febrero":
                monthInt = 2;
                break;
            case "marzo":
                monthInt = 3;
                break;
            case "abril":
                monthInt = 4;
                break;
            case "mayo":
                monthInt = 5;
                break;
            case "junio":
                monthInt = 6;
                break;
            case "julio":
                monthInt = 7;
                break;
            case "agosto":
                monthInt = 8;
                break;
            case "septiembre":
                monthInt = 9;
                break;
            case "octubre":
                monthInt = 10;
                break;
            case "noviembre":
                monthInt = 11;
                break;
            case "diciembre":
                monthInt = 12;
                break;
            default:
                break;
        }
    }

    public void setTelegramUserID(long userId){
        this.telegramUserID = userId;
    }

    public void setIsNewUser(boolean isNewUser){
        this.isNewUser = isNewUser;
    }

}
