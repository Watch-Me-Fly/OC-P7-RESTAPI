package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // create __________________________________
    public void createUser(User user) {
        log.info("[UserService] - Entered createUser");

        if (user == null) {
            // todo : translate
            throw new IllegalArgumentException("User est null");
        }
        try {
            userRepository.save(user);
            log.info("[UserService] - Exit createUser");
        } catch (Exception e) {
            log.error("[UserService] - Error saving user : " + e.getMessage());
        }
    }

    // read ____________________________________
    public User getUserById(Integer id) {
        log.info("[UserService] -  Entered getUserById");
        try {
            return userRepository.findById(id).orElse(null);
        } catch (Exception e) {
            log.error(e);
            throw new RuntimeException("Erreur à la restitution des données : " + e.getMessage());
        }
    }

    public boolean checkIfUserExists(Integer id) {
        log.info("[UserService] -  Entered checkIfUserExists");
        return userRepository.existsById(id);
    }

    // update __________________________________
    public void updateUser(User user) {
        log.info("[UserService] -  Entered updateUser");
        try {
            User oldUser = userRepository.findById(user.getId())
                    .orElse(null);

            if (oldUser != null) {
                userRepository.save(user);
                log.info("[UserService] - Exit updateUser");
            } else {
                log.error("[updateUser] - user is not found");
                throw new IllegalArgumentException("L'utilisateur est non trouvé");
            }
        } catch (Exception e) {
            log.error("[UserService] - error updating user : {}", e.getMessage());
            throw new RuntimeException("Erreur à la mise à jour du point de courbe : " + e.getMessage());
        }
    }

    // delete __________________________________
    public void deleteUser(Integer id) {
        log.info("[UserService] - Entered deleteUser");
        try {
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
            } else {
                log.error("[deleteUser] - user not found");
                throw new IllegalArgumentException("L'utilisateur est non trouvé");
            }
        } catch (Exception e) {
            log.error("[updateBidList] - error deleting user {}", e.getMessage());
            throw new RuntimeException("Erreur à la suppression : " + e.getMessage());
        }
    }

}