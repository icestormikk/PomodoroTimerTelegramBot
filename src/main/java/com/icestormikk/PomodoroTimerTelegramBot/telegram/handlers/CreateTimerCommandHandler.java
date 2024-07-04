package com.icestormikk.PomodoroTimerTelegramBot.telegram.handlers;

import com.icestormikk.PomodoroTimerTelegramBot.telegram.PomodoroTelegramBotState;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.interfaces.CommandHandler;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CreateTimerCommandHandler implements CommandHandler {
    private PomodoroTelegramBotState state;

    @Override
    public BotApiMethod<?> handle(Update update) {
        String userInput = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

        // TODO

        return null;
    }
}
