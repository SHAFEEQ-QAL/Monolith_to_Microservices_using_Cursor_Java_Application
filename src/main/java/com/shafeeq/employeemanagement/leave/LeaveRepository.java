package com.shafeeq.employeemanagement.leave;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface LeaveRepository extends JpaRepository<LeaveRequest, Long> { List<LeaveRequest> findByEmployeeId(Long employeeId); }
