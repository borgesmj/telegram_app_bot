package com.saburo.telegrambot.bot;

/**
 * ErrorsHandler
 */
public class ErrorsHandler {
    private String errorMessage;
    public ErrorsHandler(){
        this.errorMessage = "";
    }

    public void setErrorMessage(String errorText){
        this.errorMessage = errorText;
    }

    public String getErrorMessage(){
        return this.errorMessage;
    }
}