package comn.noom.interview.fullstack.sleep.controllers;

import comn.noom.interview.fullstack.sleep.dtos.SleepAverageResponse;
import comn.noom.interview.fullstack.sleep.dtos.SleepRequest;
import comn.noom.interview.fullstack.sleep.dtos.SleepResponse;
import comn.noom.interview.fullstack.sleep.service.SleepLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
public class SleepController {

    private final SleepLogService sleepService;

    public SleepController(SleepLogService sleepService) {
        this.sleepService = sleepService;
    }

    @PostMapping("/log")
    public ResponseEntity<String> logSleep(@RequestBody SleepRequest request) {
        try {
            sleepService.createSleepLog(request);
        } catch (Exception e) {
            log.warn("Bad request: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/last")
    public ResponseEntity<SleepResponse> getLastSleep(@RequestParam UUID userId) {
        Optional<SleepResponse> sleepResponse = sleepService.getLastSleep(userId);
        return sleepResponse.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/monthAverage")
    public ResponseEntity<SleepAverageResponse> getStats(@RequestParam UUID userId) {
        Optional<SleepAverageResponse> stats = sleepService.getMonthAverage(userId);
        return stats.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
