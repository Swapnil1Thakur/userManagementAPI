package com.example.userManagementAPI.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

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
    private String email;
}
