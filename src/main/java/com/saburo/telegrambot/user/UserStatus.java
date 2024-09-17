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

    /**
     * Constructor de la clase UserStatus.
     * Inicializa los Maps como un {@link HashMap}.
     */
    public UserStatus() {
        this.isWaitingForNewUsername = new HashMap<>();
    }
    // Getters
    public boolean getIsWaitingForNewUsername(long userId) {
        return isWaitingForNewUsername.getOrDefault(userId, false);
    }
    // setters
    public void setIsWaitingForNewUsername(long userId, boolean status) {
        isWaitingForNewUsername.put(userId, status);
    }
}
