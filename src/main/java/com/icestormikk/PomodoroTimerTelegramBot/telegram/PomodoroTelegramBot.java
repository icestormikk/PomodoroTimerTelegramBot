package com.icestormikk.PomodoroTimerTelegramBot.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
public class PomodoroTelegramBot extends TelegramLongPollingBot {
    @Value("${BOT_NAME}")
    public String botName;

    public PomodoroTelegramBot(@Value("${BOT_TOKEN}") String botToken) {
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return botName;
    }
}
