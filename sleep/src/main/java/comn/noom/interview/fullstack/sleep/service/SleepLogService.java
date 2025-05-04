package comn.noom.interview.fullstack.sleep.service;

import comn.noom.interview.fullstack.sleep.dtos.SleepAverageResponse;
import comn.noom.interview.fullstack.sleep.dtos.SleepRequest;
import comn.noom.interview.fullstack.sleep.dtos.SleepResponse;
import comn.noom.interview.fullstack.sleep.entity.SleepLog;
import comn.noom.interview.fullstack.sleep.repository.SleepLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SleepLogService {

    private final SleepLogRepository repository;

    public SleepLogService(SleepLogRepository repository) {
        this.repository = repository;
    }

    public void createSleepLog(SleepRequest request) {
        SleepLog log = SleepLog.builder()
                .userId(request.getUserId())
                .sleepDate(request.getSleepDate() == null ? LocalDate.now() : request.getSleepDate())
                .bedTime(request.getBedTime())
                .wakeTime(request.getWakeTime())
                .totalMinutesInBed(request.getTotalMinutesInBed())
                .mood(SleepLog.Mood.valueOf(request.getMood()))
                .build();

        repository.save(log);
    }

    public Optional<SleepResponse> getLastSleep(UUID userId) {
        Optional<SleepLog> logOptional = repository.findTopByUserIdOrderBySleepDateDesc(userId);
        if (logOptional.isEmpty()) {
            return Optional.empty();
        }
        SleepLog log = logOptional.get();
        return Optional.of(SleepResponse.builder()
                .sleepDate(log.getSleepDate())
                .bedTime(log.getBedTime())
                .wakeTime(log.getWakeTime())
                .totalMinutesInBed(log.getTotalMinutesInBed())
                .mood(String.valueOf(log.getMood()))
                .build());
    }


    public Optional<SleepAverageResponse> getMonthAverage(UUID userId) {
        LocalDate to = LocalDate.now();
        LocalDate from = to.minusDays(30);
        List<SleepLog> logs = repository.findAllByUserIdAndSleepDateBetween(userId, from, to);

        if (logs.isEmpty()) {
            log.warn("No sleep data {} {}", SleepLog.class.getName(), userId.toString());
            return Optional.empty();
        }

        int avgMinutes = (int) logs.stream().mapToInt(SleepLog::getTotalMinutesInBed).average().orElse(0);
        LocalTime avgBed = averageTime(logs.stream().map(SleepLog::getBedTime).toList());
        LocalTime avgWake = averageTime(logs.stream().map(SleepLog::getWakeTime).toList());

        Map<String, Long> moodFreq = logs.stream()
                .collect(Collectors.groupingBy(log -> log.getMood().name(), Collectors.counting()));

        return Optional.of(new SleepAverageResponse(from, to, avgMinutes, avgBed, avgWake, moodFreq));
    }

    private LocalTime averageTime(List<LocalTime> times) {
        long total = times.stream()
                .mapToLong(LocalTime::toSecondOfDay).sum();
        return LocalTime.ofSecondOfDay(total / times.size());
    }
}
