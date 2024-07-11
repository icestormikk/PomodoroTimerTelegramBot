package com.icestormikk.PomodoroTimerTelegramBot.domain.exceptions;

public class PomodoroTimerAlreadyExists extends Exception {
    public PomodoroTimerAlreadyExists() {
        super("An object of the PomodoroTimer class with such data already exists");
    }
}
