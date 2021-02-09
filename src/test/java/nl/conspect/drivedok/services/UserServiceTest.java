package nl.conspect.drivedok.services;

import nl.conspect.drivedok.exceptions.UserNotFoundException;
import nl.conspect.drivedok.model.User;
import nl.conspect.drivedok.model.Vehicle;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserServiceTest {

    UserService userService;

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    UserRepository userRepository;

    User user1;
    User user2;

    @BeforeEach
    public void init() {
        userService = new UserService(userRepository);
        user1 = new User("Toos", "abc@xyz.nl", "password123");
        user2 = new User("Miep", "bdd@zzy.nl", "zomer123");
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
        userService.save(newUser);
        assertEquals(300, userService.findAll().size());
    }

    @Test
    @DisplayName("Change the name for user1 and update. Expect the subsequent found User to have the updated name")
    void createOrUpdate() {
        var toos = userService.getById(user1.getId());
        assertEquals("Toos", toos.getName());
        toos.setName("Tante Toos");
        userService.save(toos);

        var tanteToos = userService.getById(user1.getId());
        assertEquals("Tante Toos", tanteToos.getName());
    }

    @Test
    @DisplayName("Change the password for user2 and update. Expect the updated user to have the new password")
    void update() {
        var miep = userService.getById(user2.getId());
        assertEquals("zomer123", miep.getPassword());
        miep.setPassword("qwerty");
        var updatedMiep = userService.update(miep.getId(), miep);

        assertEquals("qwerty", updatedMiep.getPassword());
    }

    @Test
    @DisplayName("Expect findAll() to return a list with size 2, then after deleting a user by id expect the list to be size 1")
    void deleteById() {
        assertEquals(2, userService.findAll().size());
        userService.deleteById(user2.getId());
        assertEquals(1, userService.findAll().size());
    }

    @Test
    @DisplayName("Expect findAll() to return a list with size 2, then after deleting a user expect the list to be size 1")
    void delete() {
        assertEquals(2, userService.findAll().size());
        userService.delete(user2);
        assertEquals(1, userService.findAll().size());
    }

    @Test
    @DisplayName("Calling addVehiclesByUserId with invalid id should result in UserNotFoundException being thrown")
    void userNotFoundException() {
        assertThrows(UserNotFoundException.class, () -> userService.addVehicleByUserId(-1L, new Vehicle()));
    }

    @Test
    @DisplayName("Expect initial collection of vehicles to be empty, then size 1 after adding a vehicle to user")
    void addVehicleByUserId() {
        assertTrue(user1.getVehicles().isEmpty());
        var user = userService.addVehicleByUserId(user1.getId(), new Vehicle());
        assertEquals(1, user.getVehicles().size());
    }

    @Test
    @DisplayName("Call getById with null, expect UserNotFoundException to be thrown")
    void getByIdThrowsExceptionWhenPassedNull() {
        assertThrows(UserNotFoundException.class, () -> userService.getById(null));
    }

    @Test
    @DisplayName("Call getById with user1 id, expect return value not null and to equals user1")
    void getById() {
        var nonNullUser = userService.getById(user1.getId());
        assertNotNull(nonNullUser);
        assertEquals(user1, nonNullUser);
    }

    @Test
    @DisplayName("Calling getById with null should throw a UserNotFoundException")
    void getByIdWithNull() {
        assertThrows(UserNotFoundException.class, () -> userService.getById(null));
    }
}
