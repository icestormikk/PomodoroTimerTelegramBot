package com.icestormikk.PomodoroTimerTelegramBot.telegram;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class PomodoroTelegramBotConfiguration {
    @Bean
    public TelegramBotsApi telegramBotsApi(PomodoroTelegramBot pomodoroTelegramBot) throws TelegramApiException {
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(pomodoroTelegramBot);
        return api;
    }
}
