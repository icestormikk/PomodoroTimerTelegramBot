package com.icestormikk.PomodoroTimerTelegramBot.telegram.handlers;

import com.icestormikk.PomodoroTimerTelegramBot.domain.TimerManager;
import com.icestormikk.PomodoroTimerTelegramBot.domain.exceptions.PomodoroTimerNotFound;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.interfaces.CommandHandler;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
public class StartTimerCommandHandler implements CommandHandler {
    @Override
    public BotApiMethod<?> handle(Update update) {
        Long chatId = update.getMessage().getChatId();
        String userInput = update.getMessage().getText();

        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        String[] args = userInput.split("\\s");

        try {
            String label = args[1];
            TimerManager.startTimer(label);
            message.setText("Таймер успешно запущен! Удачи вам при выполнении поставленной задачи. Помните, важно сохранять концентрацию.");
        } catch (PomodoroTimerNotFound e) {
            log.error(e.getMessage());
            message.setText("Таймера с таким названием не существует. Проверьте, правильно ли вы указали название желаемого таймера");
        }

        return message;
    }
}