package com.program.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "auth_users", uniqueConstraints = @UniqueConstraint(columnNames = "phone"))
@Getter
@Setter
public class AuthUser {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String role; // RIDER / DRIVER
}
