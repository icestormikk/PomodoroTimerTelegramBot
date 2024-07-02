package com.icestormikk.PomodoroTimerTelegramBot.repositories;

import com.icestormikk.PomodoroTimerTelegramBot.domain.PomodoroTimer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PomodoroTimerRepository extends MongoRepository<PomodoroTimer, String> {}
