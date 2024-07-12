package com.icestormikk.PomodoroTimerTelegramBot.telegram;

import com.icestormikk.PomodoroTimerTelegramBot.domain.PomodoroTimerDto;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.abstraction.UserSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
public class PomodoroTimerUserSession extends UserSession {
    public PomodoroTimerDto pomodoroTimerDto;
    public PomodoroTimerState state;
}
