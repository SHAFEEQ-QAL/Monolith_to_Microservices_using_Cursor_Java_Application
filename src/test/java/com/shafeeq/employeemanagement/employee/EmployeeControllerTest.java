package com.shafeeq.employeemanagement.employee;

import com.shafeeq.employeemanagement.department.Department;
import com.shafeeq.employeemanagement.department.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest @AutoConfigureMockMvc @Transactional
class EmployeeControllerTest {
    @Autowired MockMvc mvc;
    @Autowired DepartmentRepository departments;
    private Long departmentId;

    @BeforeEach void setup() { departmentId = departments.save(new Department("Training", "London")).getId(); }

    @Test void createsEmployeeInDepartment() throws Exception {
        String body = """
                {"firstName":"Aisha","lastName":"Khan","email":"aisha@example.com",
                 "annualSalary":45000,"departmentId":%d}
                """.formatted(departmentId);
        mvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("aisha@example.com"))
                .andExpect(jsonPath("$.department.name").value("Training"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }
}
