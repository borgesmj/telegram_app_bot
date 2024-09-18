package com.saburo.telegrambot.user;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase UserStatus que gestiona el estado de un usuario en relación a si el bot está 
 * esperando una nueva entrada de parte del usuario
 * Utiliza un {@link Map} para almacenar el estado de cada usuario.
 */
public class UserStatus {

    /**
     * Un mapa que asocia el ID de Telegram del usuario con un estado booleano.
     * Si el estado es {@code true}, indica que el usuario está esperando proporcionar 
     * un nuevo username. Si es {@code false}, no está esperando.
     */
    private final Map<Long, Boolean> isWaitingForNewUsername;
    private final Map<Long, Boolean> isWaitingForNewCapital;
    private final Map<Long, Boolean> isWaitingForInitialSavings;

    /**
     * Constructor de la clase UserStatus.
     * Inicializa los Maps como un {@link HashMap}.
     */
    public UserStatus() {
        this.isWaitingForNewUsername = new HashMap<>();
        this.isWaitingForNewCapital = new HashMap<>();
        this.isWaitingForInitialSavings = new HashMap<>();
    }
    // Getters
    public boolean getIsWaitingForNewUsername(long userId) {
        return isWaitingForNewUsername.getOrDefault(userId, false);
    }

    public boolean getIsWaitingForNewCapital(long userId){
        return isWaitingForNewCapital.getOrDefault(userId, false);
    }

    public boolean getIsWaitingForInitialSavings(long userId){
        return isWaitingForInitialSavings.getOrDefault(userId, false);
    }


    // setters
    public void setIsWaitingForNewUsername(long userId, boolean status) {
        isWaitingForNewUsername.put(userId, status);
    }

    public void setIsWaitingForNewCapital(long userId, boolean status){
        isWaitingForNewCapital.put(userId, status);
    }

    public void setIsWaitingForInitialSavings(long userId, boolean status){
        isWaitingForInitialSavings.put(userId, status);
    }
}
