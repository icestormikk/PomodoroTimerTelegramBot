package com.icestormikk.PomodoroTimerTelegramBot.domain.abstraction;

public abstract class Timer extends Thread {
    public String id;
    public String label;
    public String description;
    public Long durationInMs;
}
