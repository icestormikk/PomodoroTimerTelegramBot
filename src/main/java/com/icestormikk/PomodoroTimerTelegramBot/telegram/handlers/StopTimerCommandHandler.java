package com.icestormikk.PomodoroTimerTelegramBot.telegram.handlers;

import com.icestormikk.PomodoroTimerTelegramBot.domain.TimerManager;
import com.icestormikk.PomodoroTimerTelegramBot.domain.exceptions.PomodoroTimerNotFound;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.interfaces.CommandHandler;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
public class StopTimerCommandHandler implements CommandHandler {
    @Override
    public BotApiMethod<?> handle(Update update) {
        Long chatId = update.getMessage().getChatId();
        String userInput = update.getMessage().getText();

        String[] args = userInput.split("\\s");

        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        try {
            String label = args[1];
            TimerManager.stopTimer(label);
            message.setText("Таймер с названием " + label + " был успешно остановлен.");
        } catch (PomodoroTimerNotFound e) {
            log.error(e.getMessage());
            message.setText("Таймера с таким названием не существует. Проверьте, правильно ли вы указали название желаемого таймера");
        }

        return message;
    }
}
