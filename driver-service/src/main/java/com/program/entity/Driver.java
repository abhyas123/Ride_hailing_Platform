package com.program.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(
        name = "drivers",
        indexes = {
                @Index(name = "idx_driver_phone", columnList = "phone", unique = true),
                @Index(name = "idx_driver_status", columnList = "status")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Driver {

    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, unique = true, length = 15)
    private String phone;

    @Column(length = 50)
    private String name;

    @Column(name = "profile_photo_url")
    private String profilePhotoUrl;

    @Column(nullable = false)
    @Builder.Default
    private String status = "OFFLINE"; // OFFLINE, ONLINE, BUSY

    @Column(nullable = false)
    private boolean approved;

    @Version
    private Long version;
}
