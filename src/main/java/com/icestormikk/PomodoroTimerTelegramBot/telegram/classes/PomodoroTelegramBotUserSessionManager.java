package com.icestormikk.PomodoroTimerTelegramBot.telegram.classes;

import com.icestormikk.PomodoroTimerTelegramBot.domain.PomodoroTimerDto;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.exceptions.InvalidUserSessionException;

import java.util.HashMap;
import java.util.Optional;

public class PomodoroTelegramBotUserSessionManager {
    private static final HashMap<Long, PomodoroTimerUserSession> userSessions = new HashMap<>();

    public static Optional<PomodoroTimerUserSession> getUserSessionByChatId(long chatId) {
        PomodoroTimerUserSession session = userSessions.get(chatId);
        return Optional.ofNullable(session);
    }

    public static PomodoroTimerUserSession addUserSession(
        long chatId, PomodoroTimerDto timerDto, PomodoroTimerState initialState
    ) throws InvalidUserSessionException {
        if (userSessions.containsKey(chatId)) {
            throw new InvalidUserSessionException("Ошибка во время добавления сессии: " +
                    "пользовательская сессия с id " + chatId + " уже существует");
        }

        return userSessions.put(chatId, new PomodoroTimerUserSession(
            timerDto, initialState == null ? PomodoroTimerState.NONE : initialState
        ));
    }

    public static PomodoroTimerUserSession updateUserSession(
        long chatId, PomodoroTimerUserSession session
    ) throws InvalidUserSessionException {
        if (!userSessions.containsKey(chatId)) {
            throw new InvalidUserSessionException("Ошибка во время обновления сессии: " +
                    "не удалось найти пользовательскую сессию с id" + chatId);
        }
        return userSessions.put(chatId, session);
    }

    public static void deleteUserSession(long chatId) throws InvalidUserSessionException {
        if (!userSessions.containsKey(chatId)) {
            throw new InvalidUserSessionException("Ошибка во время удаления сессии: " +
                    "не удалось найти пользовательскую сессию с id" + chatId);
        }

        userSessions.remove(chatId);
    }
}
