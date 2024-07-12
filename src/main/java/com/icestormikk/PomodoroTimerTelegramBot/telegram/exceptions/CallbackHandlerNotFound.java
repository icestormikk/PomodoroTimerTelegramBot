package com.icestormikk.PomodoroTimerTelegramBot.telegram.exceptions;

public class CallbackHandlerNotFound extends Exception {
    public CallbackHandlerNotFound() {
        super("The handler for the received callback could not be found");
    }
}
