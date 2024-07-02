package com.icestormikk.PomodoroTimerTelegramBot.domain.exceptions;

public class PomodoroTimerNotFound extends RuntimeException {
    public PomodoroTimerNotFound() {
        super("Объект класса PomodoroTimer, подходящий под параметры запроса, не найден");
    }
}
