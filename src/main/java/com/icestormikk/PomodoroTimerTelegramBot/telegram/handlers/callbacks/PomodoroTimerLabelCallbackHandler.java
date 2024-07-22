package com.icestormikk.PomodoroTimerTelegramBot.telegram.handlers.callbacks;

import com.icestormikk.PomodoroTimerTelegramBot.telegram.classes.PomodoroTelegramBotUserSessionManager;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.classes.PomodoroTimerState;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.classes.PomodoroTimerUserSession;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.exceptions.InvalidUserSessionException;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.interfaces.CallbackHandler;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Slf4j
public class PomodoroTimerLabelCallbackHandler implements CallbackHandler {
    @Override
    public BotApiMethod<?> handle(Update update) throws InvalidUserSessionException {
        long chatId = update.getMessage().getChatId();
        String label = update.getMessage().getText();

        log.info("We continue the process of creating a timer. Requesting a description of the timer.");

        Optional<PomodoroTimerUserSession> optSession = PomodoroTelegramBotUserSessionManager
                .getUserSessionByChatId(chatId);
        if (optSession.isEmpty()) {
            throw new InvalidUserSessionException(
                "The desired session could not be found. The user session with the required id is missing from the list."
            );
        }
        PomodoroTimerUserSession session = optSession.get();

        session.pomodoroTimerDto.label = label;
        session.state = PomodoroTimerState.CREATE_TIMER_FETCHING_TASK_DESCRIPTION;

        PomodoroTelegramBotUserSessionManager.updateUserSession(chatId, session);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Введите описание для нового таймера");

        return message;
    }
}
