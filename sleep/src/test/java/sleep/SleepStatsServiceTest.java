package sleep;

import comn.noom.interview.fullstack.sleep.dtos.SleepAverageResponse;
import comn.noom.interview.fullstack.sleep.entity.SleepLog;
import comn.noom.interview.fullstack.sleep.repository.SleepLogRepository;
import comn.noom.interview.fullstack.sleep.service.SleepLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SleepStatsServiceTest {

    @Mock
    private SleepLogRepository repository;

    @InjectMocks
    private SleepLogService service;

    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
    }

    @Test
    void getMonthAverage_returnsEmptyWhenNoData() {
        when(repository.findAllByUserIdAndSleepDateBetween(any(), any(), any()))
                .thenReturn(Collections.emptyList());

        Optional<SleepAverageResponse> result = service.getMonthAverage(userId);

        assertTrue(result.isEmpty());
    }

    @Test
    void getMonthAverage_returnsAveragesCorrectly() {
        List<SleepLog> logs = List.of(
                createLog(480, LocalTime.of(22, 0), LocalTime.of(6, 0),SleepLog.Mood.GOOD),
                createLog(540, LocalTime.of(23, 0), LocalTime.of(7, 0), SleepLog.Mood.BAD),
                createLog(600, LocalTime.of(21, 30), LocalTime.of(6, 30), SleepLog.Mood.GOOD)
        );

        when(repository.findAllByUserIdAndSleepDateBetween(any(), any(), any()))
                .thenReturn(logs);

        Optional<SleepAverageResponse> result = service.getMonthAverage(userId);

        assertTrue(result.isPresent());
        SleepAverageResponse response = result.get();

        assertEquals(540, response.getAvgMinutesInBed());
        assertEquals(LocalTime.of(22, 10), response.getAvgBedTime());
        assertEquals(LocalTime.of(6, 30), response.getAvgWakeTime());

        Map<String, Long> moodFreq = response.getMoodFrequencies();
        assertEquals(2L, moodFreq.get("GOOD"));
        assertEquals(1L, moodFreq.get("BAD"));
    }


    private SleepLog createLog(int totalMinutes, LocalTime bedTime, LocalTime wakeTime, SleepLog.Mood mood) {
        return SleepLog.builder()
                .totalMinutesInBed(totalMinutes)
                .bedTime(bedTime)
                .wakeTime(wakeTime)
                .mood(mood)
                .sleepDate(LocalDate.now().minusDays(5))
                .build();
    }
}
