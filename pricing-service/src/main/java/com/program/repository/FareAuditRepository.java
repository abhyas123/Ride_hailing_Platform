package com.program.repository;

import com.program.entity.FareAudit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FareAuditRepository
        extends JpaRepository<FareAudit, UUID> {
}
