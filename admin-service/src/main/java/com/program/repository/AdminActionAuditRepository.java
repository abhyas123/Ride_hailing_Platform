package com.program.repository;

import com.program.entity.AdminActionAudit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AdminActionAuditRepository
        extends JpaRepository<AdminActionAudit, UUID> {

    List<AdminActionAudit> findByDriverId(UUID driverId);
}
