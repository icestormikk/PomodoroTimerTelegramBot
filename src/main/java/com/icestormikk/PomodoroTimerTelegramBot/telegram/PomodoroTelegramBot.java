package com.icestormikk.PomodoroTimerTelegramBot.telegram;

import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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
            String content = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (content) {
                case PomodoroTelegramBotCommands.START -> {
                    SendMessage message = new SendMessage();

                    message.setText(
                        EmojiParser.parseToUnicode(
                        """
                            :dart: Pomodoro Bot — Ваш личный ассистент концентрации!
                            Хотите повысить свою продуктивность и улучшить концентрацию? Pomodoro Bot поможет вам достичь максимальной эффективности с помощью метода помидора (tecnica del pomodoro)! 🍅
                            Что умеет наш бот?
                                
                            :small_orange_diamond: Таймеры Pomodoro — Установите 25-минутные интервалы работы с последующими короткими и длинными перерывами.
                            :small_orange_diamond: Уведомления — Бот уведомит вас о начале и окончании каждого интервала.
                            :small_orange_diamond: Статистика — Отслеживайте свою продуктивность и анализируйте прогресс.
                                                         
                            Присоединяйтесь к сообществу продуктивных людей и начинайте достигать своих целей уже сегодня с Pomodoro Bot! :high_brightness:
                            """
                        )
                    );
                    message.setChatId(chatId);
                    sendMessage(message);
                }
            }
        }
    }

    private void sendMessage(SendMessage message) {
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
