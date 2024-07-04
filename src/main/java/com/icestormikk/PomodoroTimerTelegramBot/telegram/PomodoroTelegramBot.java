package com.icestormikk.PomodoroTimerTelegramBot.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
            var response = PomodoroTelegramBotCommandHandlers.handleCommand(update);
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
