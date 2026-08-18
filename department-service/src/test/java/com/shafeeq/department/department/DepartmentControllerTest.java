package com.shafeeq.department.department;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class DepartmentControllerTest {
    @Autowired MockMvc mvc;
    @Autowired DepartmentRepository departments;

    @BeforeEach
    void clearDepartments() {
        departments.deleteAll();
    }

    @Test
    void createDepartment() throws Exception {
        mvc.perform(post("/api/departments").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Engineering\",\"location\":\"Manchester\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Engineering"))
                .andExpect(jsonPath("$.location").value("Manchester"));
    }

    @Test
    void rejectBlankDepartment() throws Exception {
        mvc.perform(post("/api/departments").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"location\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }

    @Test
    void rejectDuplicateDepartmentName() throws Exception {
        departments.saveAndFlush(new Department("Engineering", "Manchester"));
        mvc.perform(post("/api/departments").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Engineering\",\"location\":\"London\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listDepartments() throws Exception {
        Department saved = departments.saveAndFlush(new Department("Operations", "Leeds"));
        mvc.perform(get("/api/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(saved.getId().intValue()))
                .andExpect(jsonPath("$[0].name").value("Operations"))
                .andExpect(jsonPath("$[0].location").value("Leeds"));
    }

    @Test
    void findDepartmentById() throws Exception {
        Department saved = departments.saveAndFlush(new Department("Finance", "London"));
        mvc.perform(get("/api/departments/{id}", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId().intValue()))
                .andExpect(jsonPath("$.name").value("Finance"))
                .andExpect(jsonPath("$.location").value("London"));
    }

    @Test
    void return404ForUnknownDepartment() throws Exception {
        mvc.perform(get("/api/departments/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Department 999999 not found"));
    }

    @Test
    void updateDepartment() throws Exception {
        Department saved = departments.saveAndFlush(new Department("Training", "Birmingham"));
        mvc.perform(put("/api/departments/{id}", saved.getId()).contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Learning\",\"location\":\"Sheffield\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId().intValue()))
                .andExpect(jsonPath("$.name").value("Learning"))
                .andExpect(jsonPath("$.location").value("Sheffield"));
    }

    @Test
    void deleteDepartment() throws Exception {
        Department saved = departments.saveAndFlush(new Department("Human Resources", "Bristol"));
        mvc.perform(delete("/api/departments/{id}", saved.getId()))
                .andExpect(status().isNoContent());
        mvc.perform(get("/api/departments/{id}", saved.getId()))
                .andExpect(status().isNotFound());
    }
}
