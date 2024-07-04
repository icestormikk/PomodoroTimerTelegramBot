package com.icestormikk.PomodoroTimerTelegramBot.telegram.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public interface BotState {
    ExecutorService pool = Executors.newCachedThreadPool();
    List<Runnable> timers = new ArrayList<>();

    void startTimer(String timerName);
    void stopTimer(String timerName);
}
