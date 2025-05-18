package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @Transactional
@RequiredArgsConstructor
public class CurveService {

    private static final Logger log = LoggerFactory.getLogger(CurveService.class);
    private final CurvePointRepository curveRepository;
    // create __________________________________
    public void createCurvePoint(CurvePoint curvePoint) {
        log.info("[CurvePointService] -  Entered createCurvePoint");
        if (curvePoint == null) {
            throw new IllegalArgumentException("Le point de courbe est null");
        }
        try {
            curveRepository.save(curvePoint);
            log.info("[CurvePointService] - Exit createCurvePoint");
        } catch (Exception e) {
            log.error("[CurvePointService] - Error saving curve point : " + e.getMessage());
        }
    }
    // read ____________________________________
    public CurvePoint getCurvePointById(Integer id) {
        log.info("[CurvePointService] -  Entered getCurvePointById");
        try {
            return curveRepository.findById(id).orElse(null);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Erreur à la restitution des données : " + e.getMessage());
        }
    }
    public List<CurvePoint> getAllCurvePoints() {
        log.info("[CurvePointService] -  Entered getAllCurvePoints");
        return curveRepository.findAll();
    }
    public boolean checkIfCurvePointExist(Integer id) {
        log.info("[CurvePointService] -  Entered checkIfCurvePointExist");
        return curveRepository.existsById(id);
    }
    // update __________________________________
    public void updateCurvePoint(CurvePoint curvePoint) {
        log.info("[CurvePointService] -  Entered updateCurvePoint");
        try {
            CurvePoint oldCurvePoint = curveRepository.findById(curvePoint.getId())
                    .orElse(null);

            if (oldCurvePoint != null) {
                curveRepository.save(curvePoint);
                log.info("[CurvePointService] - Exit updateCurvePoint");
            } else {
                log.error("[updateCurvePoint] - curve point is not found");
                throw new IllegalArgumentException("Le point de courbe est non trouvé");
            }
        } catch (Exception e) {
            log.error("[CurvePointService] - error updating curve point : {}", e.getMessage());
            throw new RuntimeException("Erreur à la mise à jour du point de courbe : " + e.getMessage());
        }
    }
    // delete __________________________________
    public void deleteCurvePoint(Integer id) {
        log.info("[CurvePointService] - Entered deleteCurvePoint");
        try {
            if (curveRepository.existsById(id)) {
                curveRepository.deleteById(id);
            } else {
                log.error("[deleteCurvePoint] - curve point not found");
                throw new IllegalArgumentException("Le point de courbe est non trouvés");
            }
        } catch (Exception e) {
            log.error("[updateBidList] - error deleting curvePoint {}", e.getMessage());
            throw new RuntimeException("Erreur à la suppression : " + e.getMessage());
        }
    }

}