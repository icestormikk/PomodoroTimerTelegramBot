package com.icestormikk.PomodoroTimerTelegramBot.telegram.handlers.commands;

import com.icestormikk.PomodoroTimerTelegramBot.domain.PomodoroTimer;
import com.icestormikk.PomodoroTimerTelegramBot.domain.TimerManager;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.interfaces.CommandHandler;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class StartTimerCommandHandler implements CommandHandler {
    @Override
    public BotApiMethod<?> handle(Update update) {
        Long chatId = update.getMessage().getChatId();

        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        List<PomodoroTimer> pomodoroTimers = TimerManager.getAllPomodoroTimers().toList();

        String messageBody = createMessageBody(pomodoroTimers);
        InlineKeyboardMarkup messageMarkup = createCustomInlineKeyboardMarkup(pomodoroTimers);

        message.setText(messageBody);
        message.setReplyMarkup(messageMarkup);

        return message;
    }

    private String createMessageBody(List<PomodoroTimer> timers) {
        String header = timers.isEmpty()
                ? "На данный момент не создано ни одного таймера"
                : String.format("Ниже представлен список всех имеющихся таймеров (%d):", timers.size());
        String timersInfo = timers
                .stream()
                .map((timer) -> String.format("%s (%s ms)", timer.label, timer.durationInMs))
                .collect(Collectors.joining("\n"));

        return String.join("\n", header, timersInfo);
    }

    private InlineKeyboardMarkup createCustomInlineKeyboardMarkup(List<PomodoroTimer> timers) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> buttons = IntStream.range(0, timers.size())
                .mapToObj((i) -> {
                    InlineKeyboardButton button = new InlineKeyboardButton();
                    button.setText(String.valueOf(i));
                    button.setCallbackData(timers.get(i).id);

                    return button;
                })
                .toList();
        inlineKeyboardMarkup.setKeyboard(List.of(buttons));

        return inlineKeyboardMarkup;
    }
}