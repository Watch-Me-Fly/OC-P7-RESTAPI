package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class TradeService {

    private final TradeRepository tradeRepository;

    // create __________________________________
    public void createTrade(Trade trade) {
        log.info("[TradeService] - Entered createTrade");

        if (trade == null) {
            throw new IllegalArgumentException("Transaction est nulle");
        }
        try {
            tradeRepository.save(trade);
            log.info("[TradeService] - Exit createTrade");
        } catch (Exception e) {
            log.error("[TradeService] - Error saving trade : " + e.getMessage());
        }
    }

    // read ____________________________________
    public Trade getTradeById(Integer id) {
        log.info("[TradeService] -  Entered getTradeById");
        try {
            return tradeRepository.findById(id).orElse(null);
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException("Erreur à la restitution des données : " + e.getMessage());
        }
    }

    public List<Trade> getAllTrades() {
        log.info("[TradeService] -  Entered getAllTrades");
        return tradeRepository.findAll();
    }

    public boolean checkIfTradeExists(Integer id) {
        log.info("[TradeService] -  Entered checkIfTradeExists");
        return tradeRepository.existsById(id);
    }

    // update __________________________________
    public void updateTrade(Trade trade) {
        log.info("[TradeService] -  Entered updateTrade");
        try {
            Trade oldTrade = tradeRepository.findById(trade.getTradeId())
                    .orElse(null);

            if (oldTrade != null) {
                tradeRepository.save(trade);
                log.info("[TradeService] - Exit updateTrade");
            } else {
                log.error("[updateTrade] - trade is not found");
                throw new IllegalArgumentException("Transaction est non trouvée");
            }
        } catch (Exception e) {
            log.error("[TradeService] - error updating trade : {}", e.getMessage());
            throw new RuntimeException("Erreur à la mise à jour de la transaction : " + e.getMessage());
        }
    }

    // delete __________________________________
    public void deleteTrade(Integer id) {
        log.info("[TradeService] - Entered deleteTrade");
        try {
            if (tradeRepository.existsById(id)) {
                tradeRepository.deleteById(id);
            } else {
                log.error("[deleteTrade] - trade not found");
                throw new IllegalArgumentException("Transaction est non trouvée");
            }
        } catch (Exception e) {
            log.error("[updateBidList] - error deleting Trade {}", e.getMessage());
            throw new RuntimeException("Erreur à la suppression : " + e.getMessage());
        }
    }

}