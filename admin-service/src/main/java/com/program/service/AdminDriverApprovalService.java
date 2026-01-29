package com.program.service;

import com.program.client.DriverServiceClient;
import com.program.dto.request.ApproveDriverRequest;
import com.program.dto.request.RejectDriverRequest;
import com.program.dto.response.DriverDetailsResponse;
import com.program.dto.response.PendingDriverResponse;
import com.program.exception.DriverAlreadyApprovedException;
import com.program.util.AdminActionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminDriverApprovalService {

    private final DriverServiceClient driverServiceClient;
    private final AdminAuditService auditService;

    // ===============================
    // GET PENDING DRIVERS
    // ===============================
    public List<PendingDriverResponse> getPendingDrivers() {
        return driverServiceClient.getPendingDrivers();
    }

    // ===============================
    // GET DRIVER DETAILS
    // ===============================
    public DriverDetailsResponse getDriverDetails(String driverId) {
        return driverServiceClient.getDriverDetails(driverId);
    }

    // ===============================
    // APPROVE DRIVER
    // ===============================
    public void approveDriver(ApproveDriverRequest request) {

        boolean alreadyApproved =
                driverServiceClient.isDriverApproved(request.getDriverId());

        if (alreadyApproved) {
            throw new DriverAlreadyApprovedException("Driver already approved");
        }

        driverServiceClient.approveDriver(request.getDriverId());

        auditService.logAction(
                request.getDriverId(),
                AdminActionType.APPROVE_DRIVER,
                null
        );
    }

    // ===============================
    // REJECT DRIVER
    // ===============================
    public void rejectDriver(RejectDriverRequest request) {

        driverServiceClient.rejectDriver(
                request.getDriverId(),
                request.getReason()
        );

        auditService.logAction(
                request.getDriverId(),
                AdminActionType.REJECT_DRIVER,
                request.getReason()
        );
    }
}
