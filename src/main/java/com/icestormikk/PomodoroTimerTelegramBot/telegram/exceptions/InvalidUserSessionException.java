package com.icestormikk.PomodoroTimerTelegramBot.telegram.exceptions;

public class InvalidUserSessionException extends Exception {
    public InvalidUserSessionException(String message) {
        super(message);
    }
}
