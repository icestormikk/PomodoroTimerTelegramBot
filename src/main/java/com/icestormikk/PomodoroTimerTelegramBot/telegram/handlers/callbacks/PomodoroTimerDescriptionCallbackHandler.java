package com.icestormikk.PomodoroTimerTelegramBot.telegram.handlers.callbacks;

import com.icestormikk.PomodoroTimerTelegramBot.domain.PomodoroTimer;
import com.icestormikk.PomodoroTimerTelegramBot.domain.TimerManager;
import com.icestormikk.PomodoroTimerTelegramBot.domain.exceptions.PomodoroTimerAlreadyExists;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.PomodoroTelegramBot;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.PomodoroTimerUserSession;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.exceptions.InvalidUserSessionException;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.interfaces.CallbackHandler;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
public class PomodoroTimerDescriptionCallbackHandler implements CallbackHandler {
    @Override
    public BotApiMethod<?> handle(Update update) throws InvalidUserSessionException {
        long chatId = update.getMessage().getChatId();
        String description = update.getMessage().getText();

        PomodoroTimerUserSession userSession = PomodoroTelegramBot.userSessions.get(chatId);
        if (userSession == null) {
            throw new InvalidUserSessionException(
                "The desired session could not be found. The user session with the required id is missing from the list."
            );
        }

        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        userSession.pomodoroTimerDto.description = description;
        try {
            TimerManager.addTimer(new PomodoroTimer(userSession.pomodoroTimerDto.label, userSession.pomodoroTimerDto.description));
            PomodoroTelegramBot.userSessions.remove(chatId);

            message.setText(
                String.format(
                    "Таймер %s успешно создан. Вы можете запустить его командой \"/timerstart %s\".",
                    userSession.pomodoroTimerDto.label,
                    userSession.pomodoroTimerDto.label
                )
            );
        } catch (PomodoroTimerAlreadyExists e) {
            log.error(e.getMessage());
            message.setText("К сожалению, таймер с такими данными уже существует. Удалите предыдущий таймер или измените данные.");
        }

        return message;
    }
}
