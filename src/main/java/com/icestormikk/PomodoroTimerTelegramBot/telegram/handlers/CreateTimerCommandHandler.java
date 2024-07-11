package com.icestormikk.PomodoroTimerTelegramBot.telegram.handlers;

import com.icestormikk.PomodoroTimerTelegramBot.domain.PomodoroTimer;
import com.icestormikk.PomodoroTimerTelegramBot.domain.TimerManager;
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
        String messageText = update.getMessage().getText();

        String[] args = messageText.split("\\s");
        try {
            String label = args[1];
            Long durationInMs = Long.parseLong(args[2]);

            log.info("Creating a timer named {} for {} milliseconds", label, durationInMs);
            TimerManager.addTimer(new PomodoroTimer(label, durationInMs));

            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText("Таймер с именем " + label + " на " + durationInMs + "мс успешно создан");

            return message;
        } catch (NumberFormatException e) {
            log.error("The timer duration could not be obtained ({})", args[2]);
            throw e;
        }
    }
}
