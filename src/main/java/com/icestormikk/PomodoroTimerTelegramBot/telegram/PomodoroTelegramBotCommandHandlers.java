package com.icestormikk.PomodoroTimerTelegramBot.telegram;

import com.icestormikk.PomodoroTimerTelegramBot.telegram.exceptions.CommandHandlerNotFound;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.handlers.commands.CreateTimerCommandHandler;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.handlers.commands.StartCommandHandler;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.handlers.commands.StartTimerCommandHandler;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.handlers.commands.StopTimerCommandHandler;
import com.icestormikk.PomodoroTimerTelegramBot.telegram.interfaces.CommandHandler;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Slf4j
public class PomodoroTelegramBotCommandHandlers {
    private static final Map<String, CommandHandler> commandsMap = Map.of(
        PomodoroTelegramBotCommands.START, new StartCommandHandler(),
        PomodoroTelegramBotCommands.TIMER_CREATE, new CreateTimerCommandHandler(),
        PomodoroTelegramBotCommands.TIMER_START, new StartTimerCommandHandler(),
        PomodoroTelegramBotCommands.TIMER_STOP, new StopTimerCommandHandler()
    );

    public static BotApiMethod<?> handleCommand(Update update) throws CommandHandlerNotFound {
        String userInput = update.getMessage().getText();
        CommandHandler handler = commandsMap.get(userInput.split("\\s")[0]);

        if (handler == null) {
            log.error("Couldn't find a handler for the command {}", userInput);
            throw new CommandHandlerNotFound();
        }

        BotApiMethod<?> response = handler.handle(update);
        log.info("The message was successfully processed");
        return response;
    }
}
