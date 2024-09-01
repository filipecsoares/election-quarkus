package infrastructure.schedulers;

import domain.annotations.Principal;
import infrastructure.repositories.ElectionRepositoryRedis;
import infrastructure.repositories.ElectionRepositorySQL;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Sync {
    private final ElectionRepositorySQL sqlRepository;
    private final ElectionRepositoryRedis redisRepository;

    public Sync(@Principal ElectionRepositorySQL sqlRepository, ElectionRepositoryRedis redisRepository) {
        this.sqlRepository = sqlRepository;
        this.redisRepository = redisRepository;
    }

    // Sync every 20 seconds
    @Scheduled(cron = "*/20 * * * * ?")
    void sync() {
        sqlRepository.findAll().forEach(election -> sqlRepository.sync(redisRepository.sync(election)));
    }
}
