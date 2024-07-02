package com.icestormikk.PomodoroTimerTelegramBot.services;

import com.icestormikk.PomodoroTimerTelegramBot.domain.PomodoroTimer;
import com.icestormikk.PomodoroTimerTelegramBot.domain.PomodoroTimerDto;

import java.util.List;

public interface PomodoroTimerService {
    List<PomodoroTimer> getAllTimers();
    PomodoroTimer getTimerById(String id);
    PomodoroTimer createTimer(PomodoroTimerDto pomodoroTimerDto);
    PomodoroTimer updateTimerById(String id, PomodoroTimerDto pomodoroTimerDto);
    void deleteTimerById(String id);
}
