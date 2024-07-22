package com.icestormikk.PomodoroTimerTelegramBot.telegram.classes;

import com.icestormikk.PomodoroTimerTelegramBot.telegram.exceptions.CallbackHandlerNotFound;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.exceptions.CommandHandlerNotFound;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.exceptions.InvalidUserSessionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Optional;

@Slf4j
@Component
public class PomodoroTelegramBot extends TelegramLongPollingBot {
    public final String botName;

    public PomodoroTelegramBot(
        @Value("${BOT_NAME}") String botName,
        @Value("${BOT_TOKEN}") String botToken
    ) {
        super(botToken);
        this.botName = botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            BotApiMethod<?> response;

            try {
                long chatId = update.getMessage().getChatId();
                Optional<PomodoroTimerUserSession> session = PomodoroTelegramBotUserSessionManager
                        .getUserSessionByChatId(chatId);

                if (session.isPresent() && session.get().state != PomodoroTimerState.NONE) {
                    response = PomodoroTelegramBotCallbackHandlers.handleCallback(update, session.get());
                } else {
                    response = PomodoroTelegramBotCommandHandlers.handleCommand(update);
                }
            } catch (CommandHandlerNotFound | CallbackHandlerNotFound e) {
                log.error(e.getMessage());
                response = new SendMessage(
                    Long.toString(update.getMessage().getChatId()),
                    "Извините, мне не удалось понять, что вы имели в виду.."
                );
            }

            if (response != null) {
                sendMessage(response);
            }
        }
    }

    public void sendMessage(BotApiMethod<?> message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Произошла ошибка во время отправки сообщения: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }
}
