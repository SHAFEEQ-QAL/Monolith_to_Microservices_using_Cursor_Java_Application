package com.shafeeq.employeemanagement.leave;

import com.shafeeq.employeemanagement.common.ResourceNotFoundException;
import com.shafeeq.employeemanagement.employee.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service @Transactional
public class LeaveService {
    private final LeaveRepository repository; private final EmployeeService employeeService;
    public LeaveService(LeaveRepository repository, EmployeeService employeeService) { this.repository = repository; this.employeeService = employeeService; }
    public LeaveRequest create(LeaveRequestCreate r) { if (r.endDate().isBefore(r.startDate())) throw new IllegalArgumentException("End date cannot be before start date"); LeaveRequest leave = new LeaveRequest(); leave.setEmployee(employeeService.findById(r.employeeId())); leave.setStartDate(r.startDate()); leave.setEndDate(r.endDate()); leave.setReason(r.reason()); leave.setStatus(LeaveStatus.PENDING); return repository.save(leave); }
    @Transactional(readOnly = true) public List<LeaveRequest> findAll(Long employeeId) { return employeeId == null ? repository.findAll() : repository.findByEmployeeId(employeeId); }
    public LeaveRequest decide(Long id, LeaveStatus status) { if (status == LeaveStatus.PENDING) throw new IllegalArgumentException("Decision must be APPROVED or REJECTED"); LeaveRequest leave = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Leave request " + id + " not found")); leave.setStatus(status); return repository.save(leave); }
}
