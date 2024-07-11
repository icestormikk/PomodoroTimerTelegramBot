package com.icestormikk.PomodoroTimerTelegramBot.domain;

import com.icestormikk.PomodoroTimerTelegramBot.domain.interfaces.TimerListeners;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;

@Slf4j
public class PomodoroTimer extends Thread {
    @Id
    public String id;
    public String label;
    public Long timeInMs;
    @Setter
    private TimerListeners listeners;

    public PomodoroTimer(String label, Long timeInMs) {
        this.timeInMs = timeInMs;
        this.label = label;
    }

    public PomodoroTimer(String id, String label, Long timeInMs) {
        this.id = id;
        this.label = label;
        this.timeInMs = timeInMs;
    }

    @Override
    public void run() {
        listeners.onTimerStart(this);
        try {
            Thread.sleep(timeInMs);
        } catch (InterruptedException e) {
            log.error("An error occurred while the timer was running with id {}: {}", this.id, e.getMessage());
        }
        listeners.onTimerStop(this);
    }
}
