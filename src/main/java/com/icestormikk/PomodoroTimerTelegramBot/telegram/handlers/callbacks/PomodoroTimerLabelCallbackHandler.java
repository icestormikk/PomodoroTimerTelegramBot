package com.icestormikk.PomodoroTimerTelegramBot.telegram.handlers.callbacks;

import com.icestormikk.PomodoroTimerTelegramBot.telegram.PomodoroTelegramBot;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.PomodoroTimerState;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.PomodoroTimerUserSession;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.exceptions.InvalidUserSessionException;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.interfaces.CallbackHandler;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
public class PomodoroTimerLabelCallbackHandler implements CallbackHandler {
    @Override
    public BotApiMethod<?> handle(Update update) throws InvalidUserSessionException {
        long chatId = update.getMessage().getChatId();
        String label = update.getMessage().getText();

        log.info("We continue the process of creating a timer. Requesting a description of the timer.");

        PomodoroTimerUserSession userSession = PomodoroTelegramBot.userSessions.get(chatId);
        if (userSession == null) {
            throw new InvalidUserSessionException(
                "The desired session could not be found. The user session with the required id is missing from the list."
            );
        }

        userSession.pomodoroTimerDto.label = label;
        userSession.state = PomodoroTimerState.FETCHING_TASK_DESCRIPTION;

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Введите описание для нового таймера");

        return message;
    }
}
