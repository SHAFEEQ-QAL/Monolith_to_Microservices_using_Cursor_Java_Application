package com.shafeeq.employeemanagement.department;

import com.shafeeq.employeemanagement.common.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DepartmentController.class)
@Import(GlobalExceptionHandler.class)
class DepartmentControllerTest {
    @Autowired MockMvc mvc;
    @MockitoBean DepartmentClient client;

    @Test
    void createDepartment() throws Exception {
        when(client.create(any())).thenReturn(new Department("Engineering", "Manchester"));
        mvc.perform(post("/api/departments").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Engineering\",\"location\":\"Manchester\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Engineering"))
                .andExpect(jsonPath("$.location").value("Manchester"));
        verify(client).create(new DepartmentRequest("Engineering", "Manchester"));
    }

    @Test
    void rejectBlankDepartment() throws Exception {
        mvc.perform(post("/api/departments").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\",\"location\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"));
        verifyNoInteractions(client);
    }

    @Test
    void listDepartments() throws Exception {
        when(client.findAll()).thenReturn(List.of(new Department("Operations", "Leeds")));
        mvc.perform(get("/api/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Operations"))
                .andExpect(jsonPath("$[0].location").value("Leeds"));
    }

    @Test
    void findDepartmentById() throws Exception {
        when(client.findById(1L)).thenReturn(new Department("Finance", "London"));
        mvc.perform(get("/api/departments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Finance"))
                .andExpect(jsonPath("$.location").value("London"));
    }

    @Test
    void updateDepartment() throws Exception {
        when(client.update(eq(1L), any())).thenReturn(new Department("Learning", "Sheffield"));
        mvc.perform(put("/api/departments/1").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Learning\",\"location\":\"Sheffield\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Learning"))
                .andExpect(jsonPath("$.location").value("Sheffield"));
    }

    @Test
    void deleteDepartment() throws Exception {
        mvc.perform(delete("/api/departments/1"))
                .andExpect(status().isNoContent());
        verify(client).delete(1L);
    }

    @Test
    void preserveRemote404() throws Exception {
        byte[] body = """
                {"timestamp":"2026-08-18T00:00:00Z","status":404,"message":"Department 999999 not found","validationErrors":{}}
                """.getBytes(StandardCharsets.UTF_8);
        when(client.findById(999999L)).thenThrow(HttpClientErrorException.create(
                HttpStatus.NOT_FOUND, "Not Found", HttpHeaders.EMPTY, body, StandardCharsets.UTF_8));
        mvc.perform(get("/api/departments/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Department 999999 not found"));
    }
}
