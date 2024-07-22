package com.icestormikk.PomodoroTimerTelegramBot.telegram.handlers.commands;

import com.icestormikk.PomodoroTimerTelegramBot.telegram.classes.PomodoroTelegramBotUserSessionManager;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.exceptions.InvalidUserSessionException;
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

        try {
            PomodoroTelegramBotUserSessionManager.addUserSession(chatId, null, null);
            message.setText("Введите желаемое название таймера");
        } catch (InvalidUserSessionException e) {
            log.error(e.getMessage());
            message.setText("Произошла непредвиденная ошибка. Пожалуйста повторите запрос. Если ошибка не " +
                    "исчезнет, обратитесь к администратору");
        }

        return message;
    }
}
