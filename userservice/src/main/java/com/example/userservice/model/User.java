package com.example.userservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    private String username;
    private String email;
    private String password;
    @Column(name = "mobile_number")
    private String mobileNumber;
    @OneToMany(mappedBy ="user",fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Authority> authorities;
}
