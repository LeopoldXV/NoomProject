package comn.noom.interview.fullstack.sleep.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SleepAverageResponse {
    private LocalDate fromDate;
    private LocalDate toDate;
    private int avgMinutesInBed;
    private LocalTime avgBedTime;
    private LocalTime avgWakeTime;
    private Map<String, Long> moodFrequencies;
}
