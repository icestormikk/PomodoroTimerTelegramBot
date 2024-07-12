package com.icestormikk.PomodoroTimerTelegramBot.telegram.interfaces;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CommandHandler {
    BotApiMethod<?> handle(Update update);
}
