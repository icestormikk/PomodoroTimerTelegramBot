package com.icestormikk.PomodoroTimerTelegramBot.telegram.handlers.callbacks;

import com.icestormikk.PomodoroTimerTelegramBot.domain.PomodoroTimer;
import com.icestormikk.PomodoroTimerTelegramBot.domain.TimerManager;
import com.icestormikk.PomodoroTimerTelegramBot.domain.exceptions.PomodoroTimerAlreadyExists;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.classes.PomodoroTelegramBotUserSessionManager;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.classes.PomodoroTimerUserSession;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.exceptions.InvalidUserSessionException;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.interfaces.CallbackHandler;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Slf4j
public class PomodoroTimerDescriptionCallbackHandler implements CallbackHandler {
    @Override
    public BotApiMethod<?> handle(Update update) throws InvalidUserSessionException {
        long chatId = update.getMessage().getChatId();
        String description = update.getMessage().getText();

        Optional<PomodoroTimerUserSession> optSession = PomodoroTelegramBotUserSessionManager
                .getUserSessionByChatId(chatId);
        if (optSession.isEmpty()) {
            throw new InvalidUserSessionException(
                "The desired session could not be found. The user session with the required id is missing from the list."
            );
        }
        PomodoroTimerUserSession session = optSession.get();

        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        session.pomodoroTimerDto.description = description;
        try {
            TimerManager.addTimer(new PomodoroTimer(session.pomodoroTimerDto.label, session.pomodoroTimerDto.description));
            PomodoroTelegramBotUserSessionManager.deleteUserSession(chatId);

            message.setText(
                String.format(
                    "Таймер %s успешно создан. Вы можете запустить его командой \"/timerstart %s\".",
                    session.pomodoroTimerDto.label,
                    session.pomodoroTimerDto.label
                )
            );
        } catch (PomodoroTimerAlreadyExists e) {
            log.error(e.getMessage());
            message.setText("К сожалению, таймер с такими данными уже существует. Удалите предыдущий таймер или измените данные.");
        }

        return message;
    }
}
