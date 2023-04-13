package ru.tsu.hits.creditservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tsu.hits.creditservice.entity.CreditEntity;
import ru.tsu.hits.creditservice.entity.CreditRatingEntity;
import ru.tsu.hits.creditservice.repository.CreditRatingRepository;
import ru.tsu.hits.creditservice.repository.CreditRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditRatingService {

    private final CreditRatingRepository repository;
    private final CreditRepository creditService;

    public CreditRatingEntity getByUser(String userId) {
        return repository.get(userId);
    }

    public void createRating(String userId) {
        List<CreditEntity> credits = creditService.findByUserId(userId);
        if (credits.size() == 1) {
            CreditRatingEntity entity = new CreditRatingEntity();
            entity.setUserId(userId);
            entity.setReturnProbability(0.5F);
            repository.save(entity);
        } else {
            updateCreditRating(userId, credits);
        }
    }

    public void updateRating(String userId) {
        List<CreditEntity> credits = creditService.findByUserId(userId);
        updateCreditRating(userId, credits);
    }

    private void updateCreditRating(String userId, List<CreditEntity> credits) {
        CreditRatingEntity rating = repository.get(userId);
        int closed = 0;
        for (CreditEntity credit : credits) {
            if (credit.isClosed()) {
                closed++;
            }
        }
        rating.setReturnProbability(closed == 0 ? 1F / (float) credits.size() : (float) closed / (float) credits.size());
        repository.save(rating);
    }
}
