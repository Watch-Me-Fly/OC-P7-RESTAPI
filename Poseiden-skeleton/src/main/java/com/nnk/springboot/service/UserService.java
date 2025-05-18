package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // create __________________________________
    public void createUser(User user) {
        log.info("[UserService] - Entered createUser");

        if (user == null) {
            throw new IllegalArgumentException("Utilisateur est null");
        }
        try {
            String hashPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashPassword);
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
            log.error(e.getMessage());
            throw new RuntimeException("Erreur à la restitution des données : " + e.getMessage());
        }
    }

    public User getUserByUsername(String username) {
        log.info("[UserService] -  Entered getUserByUsername");
        try {
            return userRepository.findByUsername(username).orElse(null);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Erreur à la restitution des données : " + e.getMessage());
        }
    }

    public boolean checkIfUserExists(Integer id) {
        log.info("[UserService] -  Entered checkIfUserExists");
        return userRepository.existsById(id);
    }

    public List<User> getAllUsers() {
        log.info("[UserService] -  Entered getAllUsers");
        return userRepository.findAll();
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
            throw new RuntimeException("Erreur à la mise à jour des informations : " + e.getMessage());
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