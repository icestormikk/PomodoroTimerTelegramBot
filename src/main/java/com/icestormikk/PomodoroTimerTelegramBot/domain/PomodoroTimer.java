package com.icestormikk.PomodoroTimerTelegramBot.domain;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

@RequiredArgsConstructor
@AllArgsConstructor
public class PomodoroTimer {
    @Id
    public String id = "";
    @NonNull
    public String label;
}
