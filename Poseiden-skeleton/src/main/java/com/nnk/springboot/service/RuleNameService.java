package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class RuleNameService {

    private final RuleNameRepository repository;

    // create __________________________________
    public void createRuleName(RuleName ruleName) {
        log.info("[RuleNameService] - Entered createRuleName");

        if (ruleName == null) {
            throw new IllegalArgumentException("Le nom de la règle est null");
        }
        try {
            repository.save(ruleName);
            log.info("[RuleNameService] - Exit createRuleName");
        } catch (Exception e) {
            log.error("[RuleNameService] - Error saving ruleName : " + e.getMessage());
        }
    }

    // read ____________________________________
    public RuleName getRuleNameById(Integer id) {
        log.info("[RuleNameService] -  Entered getRuleNameById");
        try {
            return repository.findById(id).orElse(null);
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException("Erreur à la restitution des données : " + e.getMessage());
        }
    }

    public List<RuleName> getAllRuleNames() {
        log.info("[RuleNameService] -  Entered getAllRuleNames");
        return repository.findAll();
    }

    public boolean checkIfRuleNameExist(Integer id) {
        log.info("[RuleNameService] -  Entered checkIfRuleNameExist");
        return repository.existsById(id);
    }

    // update __________________________________
    public void updateRuleName(RuleName ruleName) {
        log.info("[RuleNameService] -  Entered updateRuleName");
        try {
            RuleName oldRuleName = repository.findById(ruleName.getId())
                    .orElse(null);

            if (oldRuleName != null) {
                repository.save(ruleName);
                log.info("[RuleNameService] - Exit updateRuleName");
            } else {
                log.error("[updateRuleName] - rule name is not found");
                throw new IllegalArgumentException("Le nom de la règle est non trouvé");
            }
        } catch (Exception e) {
            log.error("[RuleNameService] - error updating rule name : {}", e.getMessage());
            throw new RuntimeException("Erreur à la mise à jour du nom de la règle : " + e.getMessage());
        }
    }

    // delete __________________________________
    public void deleteRuleName(Integer id) {
        log.info("[RuleNameService] - Entered deleteRuleName");
        try {
            if (repository.existsById(id)) {
                repository.deleteById(id);
            } else {
                log.error("[deleteRuleName] - rule name not found");
                throw new IllegalArgumentException("Le nom de la règle est non trouvés");
            }
        } catch (Exception e) {
            log.error("[updateBidList] - error deleting ruleName {}", e.getMessage());
            throw new RuntimeException("Erreur à la suppression : " + e.getMessage());
        }
    }

}
