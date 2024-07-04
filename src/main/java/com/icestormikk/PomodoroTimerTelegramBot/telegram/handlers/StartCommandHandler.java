package com.icestormikk.PomodoroTimerTelegramBot.telegram.handlers;

import com.icestormikk.PomodoroTimerTelegramBot.telegram.interfaces.CommandHandler;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
public class StartCommandHandler implements CommandHandler {
    @Override
    public BotApiMethod<?> handle(Update update) {
        long chatId = update.getMessage().getChatId();

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
                                             
                Доступные команды:
                :small_orange_diamond: /timer [продолжительность] [Название] - создать новый таймер с параметрами
                :small_orange_diamond: /timerstart [Название_таймера] - запустить таймер
                :small_orange_diamond: /timerstop [Название_таймера] - остановить таймер
                :small_orange_diamond: /stats - получить статистику
                                             
                Присоединяйтесь к сообществу продуктивных людей и начинайте достигать своих целей уже сегодня с Pomodoro Bot! :high_brightness:
                """
            )
        );
        message.setChatId(chatId);

        return message;
    }
}
