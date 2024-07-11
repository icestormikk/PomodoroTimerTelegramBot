package com.icestormikk.PomodoroTimerTelegramBot.domain.interfaces;

import com.icestormikk.PomodoroTimerTelegramBot.domain.PomodoroTimer;

public interface TimerListeners {
    void onTimerStart(PomodoroTimer timer);
    void onTimerStop(PomodoroTimer timer);
}
