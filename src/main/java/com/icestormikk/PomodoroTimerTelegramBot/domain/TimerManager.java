package com.icestormikk.PomodoroTimerTelegramBot.domain;

import com.icestormikk.PomodoroTimerTelegramBot.domain.interfaces.TimerListeners;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class TimerManager {
    private static final List<PomodoroTimer> timers = new ArrayList<>();

    private static Optional<PomodoroTimer> getPomodoroTimerById(String id) {
        return timers.stream().filter((t) -> t.id.equals(id)).findFirst();
    }

    public static void addTimer(PomodoroTimer timer) {
        log.info("Adding timer with id {}", timer.id);
        synchronized (timers) {
            timer.setListeners(
                new TimerListeners() {
                    @Override
                    public void onTimerStart(PomodoroTimer timer) {
                        log.info("Timer with id {} has been started for {}ms", timer.id, timer.timeInMs);
                    }

                    @Override
                    public void onTimerStop(PomodoroTimer timer) {
                        log.info("The timer with id {} has successfully completed its work", timer.id);
                        removeTimer(timer.id);
                    }
                }
            );
            timers.add(timer);
        }
    }

    public static void removeTimer(String timerId) {
        log.info("Removing timer with id {}", timerId);

        Optional<PomodoroTimer> optTimer = getPomodoroTimerById(timerId);
        if (optTimer.isEmpty()) {
            return;
        }

        synchronized (TimerManager.class) {
            PomodoroTimer timer = optTimer.get();
            timer.interrupt();
            timers.remove(timer);
        }
    }

    public static void startTimer(String timerId) {
        Optional<PomodoroTimer> optTimer = getPomodoroTimerById(timerId);
        if (optTimer.isEmpty()) {
            return;
        }
        PomodoroTimer timer = optTimer.get();
        timer.start();
    }

    public static void stopTimer(String timerId) {
        Optional<PomodoroTimer> optTimer = getPomodoroTimerById(timerId);
        if (optTimer.isEmpty()) {
            return;
        }
        PomodoroTimer timer = optTimer.get();
        timer.interrupt();
    }
}
