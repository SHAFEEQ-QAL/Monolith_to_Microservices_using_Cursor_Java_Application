package com.shafeeq.employeemanagement.department;

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
class DepartmentControllerTest {
    @Autowired MockMvc mvc;

    @Test void createsDepartment() throws Exception {
        mvc.perform(post("/api/departments").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Engineering\",\"location\":\"Manchester\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Engineering"));
    }

    @Test void rejectsBlankDepartment() throws Exception {
        mvc.perform(post("/api/departments").contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"\",\"location\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }
}
