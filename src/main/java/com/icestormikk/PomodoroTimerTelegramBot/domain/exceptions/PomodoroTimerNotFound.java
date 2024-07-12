package com.icestormikk.PomodoroTimerTelegramBot.domain.exceptions;

public class PomodoroTimerNotFound extends RuntimeException {
    public PomodoroTimerNotFound() {
        super("An object of the Pomodoro Timer class suitable for the request parameters was not found");
    }
}
