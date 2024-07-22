package com.icestormikk.PomodoroTimerTelegramBot.telegram.classes;

import com.icestormikk.PomodoroTimerTelegramBot.telegram.exceptions.CallbackHandlerNotFound;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.exceptions.InvalidUserSessionException;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.handlers.callbacks.PomodoroTimerDescriptionCallbackHandler;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.handlers.callbacks.PomodoroTimerLabelCallbackHandler;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.interfaces.CallbackHandler;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Slf4j
public class PomodoroTelegramBotCallbackHandlers {
    private static final Map<PomodoroTimerState, CallbackHandler> handlers = Map.of(
        PomodoroTimerState.CREATE_TIMER_FETCHING_TASK_LABEL, new PomodoroTimerLabelCallbackHandler(),
        PomodoroTimerState.CREATE_TIMER_FETCHING_TASK_DESCRIPTION, new PomodoroTimerDescriptionCallbackHandler()
    );

    public static BotApiMethod<?> handleCallback(Update update, PomodoroTimerUserSession session) throws CallbackHandlerNotFound {
        PomodoroTimerState state = session.state;
        CallbackHandler handler = handlers.get(state);

        if (handler == null) {
            log.error("Couldn't find a handler for the state {}", state.name());
            throw new CallbackHandlerNotFound();
        }

        try {
            BotApiMethod<?> response = handler.handle(update);
            log.info("The message was successfully processed");
            return response;
        } catch (InvalidUserSessionException e) {
            log.error("The message could not be processed. An error occurred during processing.");
            return null;
        }
    }
}
