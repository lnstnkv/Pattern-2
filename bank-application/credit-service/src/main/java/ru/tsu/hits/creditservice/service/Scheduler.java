package ru.tsu.hits.creditservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class Scheduler {

    private final CreditService service;

    @Autowired
    public Scheduler(CreditService service) {
        this.service = service;
    }

    @Scheduled(fixedDelay = 1000 * 3600 * 24)
    public void payDebt() {
        service.payAllDebts();
    }
}
