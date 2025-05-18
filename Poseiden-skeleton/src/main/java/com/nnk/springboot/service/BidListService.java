package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @Transactional
@Log4j2
@RequiredArgsConstructor
public class BidListService {

    private final BidListRepository bidListRepository;

    // create __________________________________
    public void createBidList(BidList bidList) {
        log.info("[BidListService] - Entered createBidList");
        if (bidList.getAccount() == null || bidList.getAccount().isEmpty()) {
            throw new IllegalArgumentException("Un compte est obligatoire");
        }
        if (bidList.getType() == null || bidList.getType().isEmpty()) {
            throw new IllegalArgumentException("Un type est obligatoire");
        }
        try {
            bidListRepository.save(bidList);
            log.info("[BidListService] - Exit createBidList");
        } catch (Exception e) {
            log.error(e);
        }
    }

    // read ____________________________________
    public BidList getBidList(int id) {
        log.info("[BidListService] - Entered getBidList");
        try {
            return bidListRepository.findById(id).orElse(null);
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException("Erreur à la restitution de données : " + e.getMessage());
        }
    }
    public List<BidList> getAllBidLists() {
        log.info("[BidListService] - Entered getBidLists");
        List<BidList> bidLists = bidListRepository.findAll();
        log.info("[BidListService] - Exit getBidLists");
        return bidLists;
    }

    // update __________________________________
    public void updateBidList(BidList bidList) {
        log.info("[BidListService] - Entered updateBidList");
        try {
            if (bidListRepository.existsById(bidList.getId())) {
                bidListRepository.save(bidList);
                log.info("[BidListService] - Exit updateBidList");
            } else {
                log.error("[updateBidList] - Le bidList n'existe pas");
                throw new IllegalArgumentException("Le bidList n'existe pas");
            }
        } catch (Exception e) {
            log.error("[BidListService] - error updating bidList : {}", e.getMessage());
            throw new RuntimeException("Erreur à la mise à jour : " + e.getMessage());
        }
    }

    // delete __________________________________
    public void deleteBidList(Integer id) {
        log.info("[BidListService] - Entered deleteBidList");
        try {
            if (bidListRepository.existsById(id)) {
                bidListRepository.deleteById(id);
                log.info("[BidListService] - Exit deleteBidList");
            } else {
                log.error("[deleteBidList] - Le bidList n'existe pas");
                throw new IllegalArgumentException("Le bidList n'existe pas");
            }
        } catch (Exception e) {
            log.error("[BidListService] - error deleting bidList {}", e.getMessage());
            throw new RuntimeException("Erreur à la suppression : " + e.getMessage());
        }
    }

}
