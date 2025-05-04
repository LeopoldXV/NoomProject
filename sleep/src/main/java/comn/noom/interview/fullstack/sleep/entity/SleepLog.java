package comn.noom.interview.fullstack.sleep.entity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "sleep_logs", uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "sleepDate"}))
@Builder
public class SleepLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID userId;
    private LocalDate sleepDate;
    private LocalTime bedTime;
    private LocalTime wakeTime;
    private int totalMinutesInBed;

    @Enumerated(EnumType.STRING)
    private Mood mood;

    private Timestamp createdAt = Timestamp.from(Instant.now());

    public enum Mood {
        BAD, OK, GOOD
    }
}
