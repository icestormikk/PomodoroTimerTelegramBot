package com.icestormikk.PomodoroTimerTelegramBot.telegram;

import com.icestormikk.PomodoroTimerTelegramBot.services.implementations.PomodoroTimerServiceImpl;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.interfaces.BotState;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PomodoroTelegramBotState implements BotState {
    @Autowired
    private PomodoroTimerServiceImpl pomodoroTimerService;

    @Override
    public void startTimer(String timerName) {
    }

    @Override
    public void stopTimer(String timerName) {

    }
}
