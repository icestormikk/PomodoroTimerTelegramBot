package com.icestormikk.PomodoroTimerTelegramBot.services.implementations;

import com.icestormikk.PomodoroTimerTelegramBot.domain.PomodoroTimer;
import com.icestormikk.PomodoroTimerTelegramBot.domain.PomodoroTimerDto;
import com.icestormikk.PomodoroTimerTelegramBot.domain.exceptions.PomodoroTimerNotFound;
import com.icestormikk.PomodoroTimerTelegramBot.repositories.PomodoroTimerRepository;
import com.icestormikk.PomodoroTimerTelegramBot.services.PomodoroTimerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class PomodoroTimerServiceImpl implements PomodoroTimerService {
    private final PomodoroTimerRepository pomodoroTimerRepository;

    @Override
    public List<PomodoroTimer> getAllTimers() {
        log.info("Получен запрос на получение всех объектов класса PomodoroTimer");
        List<PomodoroTimer> timers = pomodoroTimerRepository.findAll();

        log.info("Успешно получено {} объектов класса Timer", timers.size());
        return timers;
    }

    @Override
    public PomodoroTimer getTimerById(String id) {
        log.info("Получен запрос на получение объекта класса PomodoroTimer с id {}", id);
        Optional<PomodoroTimer> timer = pomodoroTimerRepository.findById(id);

        if (timer.isEmpty()) {
            log.warn("Во время поиска объект класса PomodoroTimer с id {} был не найден", id);
            throw new PomodoroTimerNotFound();
        }

        log.info("Объект класса PomodoroTimer с id {} успешно найден", id);
        return timer.get();
    }

    @Override
    public PomodoroTimer createTimer(PomodoroTimerDto pomodoroTimerDto) {
        log.info("Получен запрос на добавление объекта класса PomodoroTimer");

        PomodoroTimer timer = new PomodoroTimer(pomodoroTimerDto.label);
        try {
            PomodoroTimer result = pomodoroTimerRepository.save(timer);
            log.info("Объект класса PomodoroTimer успешно сохранён");
            return result;
        } catch (Exception e) {
            log.error("Во время сохранения объекта класса PomodoroTimer произошла ошибка: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public PomodoroTimer updateTimerById(String id, PomodoroTimerDto pomodoroTimerDto) {
        log.info("Получен запрос на обновление объекта класса PomodoroTimer");

        Optional<PomodoroTimer> timer = pomodoroTimerRepository.findById(id);
        if (timer.isEmpty()) {
            log.warn("Во время обновления объект класса PomodoroTimer с id {} был не найден", id);
            throw new PomodoroTimerNotFound();
        }

        PomodoroTimer updatedTimer = new PomodoroTimer(id, pomodoroTimerDto.label);
        try {
            PomodoroTimer result = pomodoroTimerRepository.save(updatedTimer);
            log.info("Обновление объекта класса PomodoroTimer c id {} прошло успешно", id);
            return result;
        } catch (Exception e) {
            log.error("Во время обновления объекта класса PomodoroTimer произошла ошибка: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public void deleteTimerById(String id) {
        Optional<PomodoroTimer> timer = pomodoroTimerRepository.findById(id);
        if (timer.isEmpty()) {
            log.warn("Во время удаления объект класса PomodoroTimer с id {} был не найден", id);
            throw new PomodoroTimerNotFound();
        }

        try {
            pomodoroTimerRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Во время удаления объекта класса PomodoroTimer произошла ошибка: {}", e.getMessage());
            throw e;
        }
    }
}
