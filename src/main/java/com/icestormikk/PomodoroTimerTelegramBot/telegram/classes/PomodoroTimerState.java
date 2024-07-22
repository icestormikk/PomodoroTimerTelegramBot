package com.icestormikk.PomodoroTimerTelegramBot.telegram.classes;

public enum PomodoroTimerState {
    NONE,
    CREATE_TIMER_FETCHING_TASK_LABEL,
    CREATE_TIMER_FETCHING_TASK_DESCRIPTION,
    START_TIMER_SELECT,
    START_TIMER_CONFIRM
}
