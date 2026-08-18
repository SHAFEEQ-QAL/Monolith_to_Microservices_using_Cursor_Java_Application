package com.shafeeq.employeemanagement.payroll;

import com.shafeeq.employeemanagement.employee.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class PayrollServiceTest {
    @Mock PayrollRepository repository;
    @Mock EmployeeService employeeService;
    @Test void rejectsDeductionsAboveGrossPay() {
        PayrollService service = new PayrollService(repository, employeeService);
        PayrollRequest request = new PayrollRequest(1L, LocalDate.now(), new BigDecimal("1000"), new BigDecimal("1200"));
        assertThrows(IllegalArgumentException.class, () -> service.create(request));
    }
}
