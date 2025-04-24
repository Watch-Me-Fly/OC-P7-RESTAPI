package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;

    // create __________________________________
    public void createRating(Rating rating) {
        log.info("[RatingService] - Entered createRating");

        if (rating == null) {
            // todo : translate
            throw new IllegalArgumentException("Rating est null");
        }
        try {
            ratingRepository.save(rating);
            log.info("[RatingService] - Exit createRating");
        } catch (Exception e) {
            log.error("[RatingService] - Error saving rating : " + e.getMessage());
        }
    }

    // read ____________________________________
    public Rating getRatingById(Integer id) {
        log.info("[RatingService] -  Entered getRatingById");
        try {
            return ratingRepository.findById(id).orElse(null);
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException("Erreur à la restitution des données : " + e.getMessage());
        }
    }

    public List<Rating> getAllRatings() {
        log.info("[RatingService] -  Entered getAllRatings");
        return ratingRepository.findAll();
    }

    public boolean checkIfRatingExist(Integer id) {
        log.info("[RatingService] -  Entered checkIfRatingExist");
        return ratingRepository.existsById(id);
    }

    // update __________________________________
    public void updateRating(Rating rating) {
        log.info("[RatingService] -  Entered updateRating");
        try {
            Rating oldRating = ratingRepository.findById(rating.getId())
                    .orElse(null);

            if (oldRating != null) {
                ratingRepository.save(rating);
                log.info("[RatingService] - Exit updateRating");
            } else {
                log.error("[updateRating] - curve point is not found");
                throw new IllegalArgumentException("Le point de courbe est non trouvé");
            }
        } catch (Exception e) {
            log.error("[RatingService] - error updating curve point : {}", e.getMessage());
            throw new RuntimeException("Erreur à la mise à jour du point de courbe : " + e.getMessage());
        }
    }

    // delete __________________________________
    public void deleteRating(Integer id) {
        log.info("[RatingService] - Entered deleteRating");
        try {
            if (ratingRepository.existsById(id)) {
                ratingRepository.deleteById(id);
            } else {
                log.error("[deleteRating] - curve point not found");
                throw new IllegalArgumentException("Le point de courbe est non trouvés");
            }
        } catch (Exception e) {
            log.error("[updateBidList] - error deleting rating {}", e.getMessage());
            throw new RuntimeException("Erreur à la suppression : " + e.getMessage());
        }
    }

}