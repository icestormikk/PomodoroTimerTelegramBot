package com.icestormikk.PomodoroTimerTelegramBot.domain;

import com.icestormikk.PomodoroTimerTelegramBot.domain.exceptions.PomodoroTimerAlreadyExists;
import com.icestormikk.PomodoroTimerTelegramBot.domain.exceptions.PomodoroTimerNotFound;
import com.icestormikk.PomodoroTimerTelegramBot.domain.interfaces.TimerListeners;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Component
public class TimerManager {
    private static final List<PomodoroTimer> timers = new ArrayList<>();
    private static final int DEFAULT_PAGE_SIZE = 10;

    private static Optional<PomodoroTimer> getPomodoroTimerById(String id) {
        return timers.stream().filter((t) -> t.id.equals(id)).findFirst();
    }

    private static Optional<PomodoroTimer> getPomodoroTimerByLabel(String label) {
        return timers.stream().filter((t) -> t.label.equals(label)).findFirst();
    }

    public static Stream<PomodoroTimer> getAllPomodoroTimers() {
        return timers.parallelStream();
    }

    public static Stream<PomodoroTimer> getPomodoroTimersByPage(int pageIndex) {
        return timers.subList(pageIndex * DEFAULT_PAGE_SIZE, (pageIndex + 1) * DEFAULT_PAGE_SIZE).parallelStream();
    }

    public static void addTimer(PomodoroTimer timer) throws PomodoroTimerAlreadyExists {
        log.info("Adding timer with id {}", timer.id);
        synchronized (timers) {
            Optional<PomodoroTimer> optTimer = getPomodoroTimerByLabel(timer.label);
            if (optTimer.isPresent()) {
                throw new PomodoroTimerAlreadyExists();
            }

            timer.setListeners(
                new TimerListeners() {
                    @Override
                    public void onTimerStart(PomodoroTimer timer) {
                        log.info("Timer with id {} has been started", timer.id);
                    }

                    @Override
                    public void onTimerStop(PomodoroTimer timer) {
                        log.info("The timer with id {} has successfully completed its work", timer.id);
                        removeTimer(timer.label);
                    }
                }
            );
            timers.add(timer);
        }
    }

    public static void removeTimer(String label) throws PomodoroTimerNotFound {
        log.info("Removing timer with label {}", label);

        Optional<PomodoroTimer> optTimer = getPomodoroTimerByLabel(label);
        if (optTimer.isEmpty()) {
            throw new PomodoroTimerNotFound();
        }

        synchronized (TimerManager.class) {
            PomodoroTimer timer = optTimer.get();
            timer.interrupt();
            timers.remove(timer);
        }
    }

    public static void startTimer(String label) throws PomodoroTimerNotFound {
        Optional<PomodoroTimer> optTimer = getPomodoroTimerByLabel(label);
        if (optTimer.isEmpty()) {
            throw new PomodoroTimerNotFound();
        }
        PomodoroTimer timer = optTimer.get();
        timer.start();
    }

    public static void stopTimer(String label) throws PomodoroTimerNotFound {
        Optional<PomodoroTimer> optTimer = getPomodoroTimerByLabel(label);
        if (optTimer.isEmpty()) {
            throw new PomodoroTimerNotFound();
        }
        PomodoroTimer timer = optTimer.get();
        timer.interrupt();
    }
}
