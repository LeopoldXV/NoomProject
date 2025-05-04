package comn.noom.interview.fullstack.sleep.repository;

import comn.noom.interview.fullstack.sleep.entity.SleepLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SleepLogRepository extends JpaRepository<SleepLog, Long> {
    Optional<SleepLog> findTopByUserIdOrderBySleepDateDesc(UUID userId);
    List<SleepLog> findAllByUserIdAndSleepDateBetween(UUID userId, LocalDate from, LocalDate to);
}
