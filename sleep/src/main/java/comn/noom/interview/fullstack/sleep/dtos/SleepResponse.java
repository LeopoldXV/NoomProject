package comn.noom.interview.fullstack.sleep.dtos;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SleepResponse {
    private LocalDate sleepDate;
    private LocalTime bedTime;
    private LocalTime wakeTime;
    private int totalMinutesInBed;
    private String mood;
}
