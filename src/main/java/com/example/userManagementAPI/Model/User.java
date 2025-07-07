package com.example.userManagementAPI.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor // Required by JPA
@RequiredArgsConstructor // For constructor with name and email
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @NotBlank(message = "name is required")
    private String name;

    @NonNull
    @NotBlank(message = "email is required")
    @Email(message = "email should be valid")
    @Column(unique=true)
    private String email;



    @NotBlank(message = "Password is required")
    private String password;


    @ElementCollection(fetch = FetchType.EAGER)

    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))

    @Column (name = "role")
    private Set<String> roles = new HashSet<>();

    private String role;




}
