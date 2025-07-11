package com.example.userManagementAPI;

import com.example.userManagementAPI.Model.User;
import com.example.userManagementAPI.Repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        objectMapper.registerModule(new JavaTimeModule());

        user1 = new User("John Doe", "john@example.com");
        user2 = new User("Charlie Smith", "charlie@example.com");

        List<User> savedInitialUsers = userRepository.saveAll(Arrays.asList(user1, user2));
        this.user1 = savedInitialUsers.get(0);
        this.user2 = savedInitialUsers.get(1);

        List<User> manyUsers = IntStream.rangeClosed(1, 25)
                .mapToObj(i -> new User("User " + i, "user" + i + "@example.com"))
                .collect(Collectors.toList());

        userRepository.saveAll(manyUsers);
    }

    @Test
    void testGetUserByIdFound() throws Exception {
        mockMvc.perform(get("/api/users/{id}", user1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) user1.getId())))

                .andExpect(jsonPath("$.name", is(user1.getName())))
                .andExpect(jsonPath("$.email", is(user1.getEmail())));
    }

    @Test
    void testCreateUser() throws Exception {
        User newUser = new User("Alice Wonderland", "alice@example.com");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name", is(newUser.getName())))
                .andExpect(jsonPath("$.email", is(newUser.getEmail())));
    }

    @Test
    void testCreateMultipleUsers() throws Exception {
        List<User> newUsers = Arrays.asList(
                new User("Alice Brown", "alice.brown@example.com"),
                new User("Rohan Gray", "rohan.gray@example.com")
        );

        mockMvc.perform(post("/api/users/multiple")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUsers)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].name", is("Alice Brown")))
                .andExpect(jsonPath("$[1].email", is("rohan.gray@example.com")));
    }

    @Test
    void testDeleteUserFound() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", user1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteUserNotFound() throws Exception {
        mockMvc.perform(delete("/api/users/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
