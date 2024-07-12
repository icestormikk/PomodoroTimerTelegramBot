package com.icestormikk.PomodoroTimerTelegramBot.telegram.handlers.commands;

import com.icestormikk.PomodoroTimerTelegramBot.domain.PomodoroTimerDto;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.PomodoroTelegramBot;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.PomodoroTimerState;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.PomodoroTimerUserSession;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.interfaces.CommandHandler;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
public class CreateTimerCommandHandler implements CommandHandler {
    @Override
    public BotApiMethod<?> handle(Update update) {
        long chatId = update.getMessage().getChatId();

        log.info("The process of creating a PomodoroTimer begins. Requesting information about the name of the timer.");
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Введите желаемое название таймера");

        PomodoroTimerUserSession userSession = new PomodoroTimerUserSession(
            new PomodoroTimerDto(null, null),
            PomodoroTimerState.FETCHING_TASK_LABEL
        );
        PomodoroTelegramBot.userSessions.put(chatId, userSession);

        return message;
    }
}
