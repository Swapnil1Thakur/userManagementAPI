package com.example.userManagementAPI.Controller;


import com.example.userManagementAPI.Model.User;
import com.example.userManagementAPI.Repository.UserRepository;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
    @PostMapping("/multiple")
    public ResponseEntity<List<User>> createMultipleUsers(@RequestBody List<User> users) {
        List<User> savedUsers = userRepository.saveAll(users);
        return new ResponseEntity<>(savedUsers, HttpStatus.CREATED);
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userRepository.findAll();           //retrieves all users from the database
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
            Optional<User> user = userRepository.findById(id);
            return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User userDetails){

        return userRepository.findById(id).map(existingUser ->
        {existingUser.setName(userDetails.getName());
            existingUser.setEmail(userDetails.getEmail());
            User updatedUser = userRepository.save(existingUser);
            return ResponseEntity.ok(updatedUser);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser (@PathVariable Long id){
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);     //delete the user
            return ResponseEntity.noContent().build();   //204 No Found
        }else{
            return ResponseEntity.notFound().build();    //if user not found, return 404 not found
        }
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    //new column timestamp
    @Column(name = "created_at" , updatable = false)      //updatable=false ensures its set only once
    @CreationTimestamp
    private LocalDateTime createdAt;

    //new - update time stamp
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @GetMapping("/page")
    public Page<User> getUsers(Pageable pageable){
        return userRepository.findAll(pageable);
    }





}
