package com.icestormikk.PomodoroTimerTelegramBot.telegram.interfaces;

import com.icestormikk.PomodoroTimerTelegramBot.telegram.exceptions.InvalidUserSessionException;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CallbackHandler {
    BotApiMethod<?> handle(Update update) throws InvalidUserSessionException;
}
