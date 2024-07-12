package com.icestormikk.PomodoroTimerTelegramBot.telegram.exceptions;

public class CommandHandlerNotFound extends RuntimeException {
    public CommandHandlerNotFound() {
        super("The handler for the received command could not be found");
    }
}
