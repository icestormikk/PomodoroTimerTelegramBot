package com.icestormikk.PomodoroTimerTelegramBot.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PomodoroTimerDto {
    public String label;
    public Long timeInMs;
}
