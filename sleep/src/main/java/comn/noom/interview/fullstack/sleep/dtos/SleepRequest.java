package comn.noom.interview.fullstack.sleep.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SleepRequest {
    private UUID userId;
    private LocalDate sleepDate;
    private LocalTime bedTime;
    private LocalTime wakeTime;
    private int totalMinutesInBed;
    private String mood;
}
