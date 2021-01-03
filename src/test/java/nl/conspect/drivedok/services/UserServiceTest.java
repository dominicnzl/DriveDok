package nl.conspect.drivedok.services;

import nl.conspect.drivedok.exceptions.UserNotFoundException;
import nl.conspect.drivedok.model.User;
import nl.conspect.drivedok.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest
class UserServiceTest {

    UserService userService;

    VehicleService vehicleService;

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    UserRepository userRepository;

    User user1;
    User user2;

    @BeforeEach
    public void init() {
        userService = new UserService(vehicleService, userRepository);
        user1 = new User("Toos", "abc@xyz.nl", "password123", Collections.emptySet());
        user2 = new User("Miep", "bdd@zzy.nl", "zomer123", Collections.emptySet());
        testEntityManager.persist(user1);
        testEntityManager.persist(user2);
    }

    @Test
    @DisplayName("Expect findAll() to return list with size 2")
    void findAll() {
        assertEquals(2, userService.findAll().size());
    }

    @Test
    @DisplayName("Expect findById to find user1 when its id is passed")
    void findById() {
        var foundUser = userService.findById(user1.getId())
                .orElseThrow(() -> new UserNotFoundException("init method did not execute properly"));
        assertEquals(user1, foundUser);
        assertNotEquals(user2, foundUser);
    }

    @Test
    @DisplayName("Expect findAll() to return a list with size 3 after new User has been created")
    void create() {
        var newUser = new User("Bep", "xyz@abc.nl", "hallo123", Collections.emptySet());
        userService.create(newUser);
        assertEquals(3, userService.findAll().size());
    }

    @Test
    @DisplayName("Change the name for user1 and update. Expect the subsequent found User to have the updated name")
    void update() {
        var toos = userService.findById(user1.getId())
                .orElseThrow(() -> new IllegalArgumentException("Toos not found, init() not executed properly"));
        assertEquals("Toos", toos.getName());
        toos.setName("Tante Toos");
        userService.update(toos);

        var tanteToos = userService.findById(user1.getId())
                .orElseThrow(() -> new IllegalArgumentException("Tante Toos not found, update() not executed properly"));
        assertEquals("Tante Toos", tanteToos.getName());
    }

    @Test
    @DisplayName("Expect findAll() to return a list with size 2, then after deleting a user expect the list to be size 1")
    void deleteById() {
        assertEquals(2, userService.findAll().size());
        userService.deleteById(user2.getId());
        assertEquals(1, userService.findAll().size());
    }
}
