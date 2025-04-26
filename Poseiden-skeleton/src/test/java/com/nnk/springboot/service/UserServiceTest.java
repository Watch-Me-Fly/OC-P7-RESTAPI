package com.nnk.springboot.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository repository;
    @InjectMocks
    private UserService service;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1);
        user.setUsername("johnDoe");
        user.setPassword("JohnP@ssw0rd");
        user.setFullname("John Doe");
        user.setRole("USER");

        service = new UserService(repository);
    }

    // Create _____________________________
    @DisplayName("Create a new user")
    @Test
    void createUser_success() {
        // Arrange
        when(repository.save(any(User.class))).thenReturn(user);
        // Act
        service.createUser(user);
        // Assign
        verify(repository, times(1)).save(any(User.class));
    }
    // Read   _____________________________
    @DisplayName("Get a user from their id")
    @Test
    void getUserById_success() {
        // Arrange
        when(repository.findById(anyInt())).thenReturn(Optional.of(user));
        // Act
        User retrieved = service.getUserById(user.getId());
        // Assign
        assertNotNull(retrieved);
        assertEquals(user.getId(), retrieved.getId());
    }
    // Update _____________________________
    @DisplayName("Update a user")
    @Test
    void updateUser_success() {
        // Arrange
        when(repository.findById(anyInt())).thenReturn(Optional.of(user));
        user.setFullname("Johnny Doe");
        // Act
        service.updateUser(user);
        // Assign
        verify(repository, times(1)).findById(anyInt());
        verify(repository, times(1)).save(any(User.class));
        assert(user.getFullname().equals("Johnny Doe"));
    }
    // Delete _____________________________
    @DisplayName("Delete user account")
    @Test
    void deleteUser_success() {
        // Arrange
        when(repository.existsById(anyInt())).thenReturn(true);
        // Act
        service.deleteUser(user.getId());
        // Assign
        verify(repository, times(1)).existsById(user.getId());
        verify(repository, times(1)).deleteById(user.getId());
    }
}
