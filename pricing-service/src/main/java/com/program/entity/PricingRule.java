package com.program.entity;

import com.program.util.VehicleType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "pricing_rules",
        indexes = {
                @Index(name = "idx_pricing_vehicle_type", columnList = "vehicle_type", unique = true)
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PricingRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type", nullable = false, length = 20)
    private VehicleType vehicleType;

    @Column(name = "base_fare", nullable = false)
    private Double baseFare;

    @Column(name = "per_km_rate", nullable = false)
    private Double perKmRate;

    @Column(name = "per_min_rate", nullable = false)
    private Double perMinuteRate;

    @Version
    private Long version;
}
