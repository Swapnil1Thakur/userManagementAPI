package com.example.userManagementAPI;

import com.example.userManagementAPI.Model.User;
import com.example.userManagementAPI.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        user1 = new User("John Doe", "john@example.com");
        user2 = new User("Charlie Smith", "charlie@example.com");
        entityManager.persist(user1);
        entityManager.persist(user2);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void testFindByEmailFound() {
        Optional<User> foundUser = userRepository.findByEmail(user1.getEmail());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo(user1.getName());
        assertThat(foundUser.get().getEmail()).isEqualTo(user1.getEmail());
        assertThat(foundUser.get().getId()).isEqualTo(user1.getId());


    }

    @Test
    void testFindByEmailNotFound() {
        Optional<User> result = userRepository.findByEmail("random@email.com");
        assertThat(result).isNotPresent(); // ya .isEmpty()
    }

    @Test
    void testSaveUser() {
        User newUser = new User("Charlie Chaplin", "charlie@example.com");

        User savedUser = userRepository.save(newUser);

        assertThat(savedUser).isNotNull();


        assertThat(savedUser.getId()).isNotNull(); // id auto generated ho rahi hai

        assertThat(savedUser.getName()).isEqualTo("Test Save");
        assertThat(savedUser.getEmail()).isEqualTo("save@test.com");
    }

    @Test
    void testDeleteUserById() {
        //create and save user
        User userToDelete = new User("delete Me", "deleteme@example.com");
        User savedUser = userRepository.save(userToDelete);

        /// delete it then
        userRepository.deleteById(savedUser.getId());

        //verify it
        Optional<User> deletedUser = userRepository.findById(savedUser.getId());
        assertThat(deletedUser).isNotPresent(); // ya .isEmpty()
    }

    @Test
    void testUpdateUser() {
        // Save original user first
        User userToUpdate = new User("Real Name", "realName@example.com");
        User savedUser = userRepository.save(userToUpdate);

        // Change the values now to match the assertion
        savedUser.setName("Updated Name");
        savedUser.setEmail("updated@example.com");

        // Save again
        User updatedUser = userRepository.save(savedUser);

        // Now verify it
        assertThat(updatedUser.getName()).isEqualTo("Updated Name");
        assertThat(updatedUser.getEmail()).isEqualTo("updated@example.com");
    }

}
