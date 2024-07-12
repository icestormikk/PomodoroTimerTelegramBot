package com.icestormikk.PomodoroTimerTelegramBot.domain;

import com.icestormikk.PomodoroTimerTelegramBot.domain.abstraction.Timer;
import com.icestormikk.PomodoroTimerTelegramBot.domain.interfaces.TimerListeners;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Setter
@Slf4j
public class PomodoroTimer extends Timer {
    private TimerListeners listeners;

    public PomodoroTimer(String label, String description) {
        this.id = UUID.randomUUID().toString();
        this.label = label;
        this.description = description;
        this.durationInMs = 1000L * 60 * 25; // 25 minutes (1000ms * 60sec * 25min)
    }

    @Override
    public void run() {
        listeners.onTimerStart(this);
        try {
            Thread.sleep(this.durationInMs);
        } catch (InterruptedException e) {
            log.error("An error occurred while the timer was running with id {}: {}", this.id, e.getMessage());
        }
        listeners.onTimerStop(this);
    }
}
