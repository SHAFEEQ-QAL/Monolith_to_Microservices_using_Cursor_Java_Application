package com.shafeeq.employeemanagement.department;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class DepartmentClient {
    private final RestClient restClient;

    public DepartmentClient(RestClient.Builder builder,
                            @Value("${department.service.base-url}") String baseUrl) {
        this.restClient = builder.baseUrl(baseUrl).build();
    }

    public Department create(DepartmentRequest request) {
        return restClient.post()
                .uri("/api/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(Department.class);
    }

    public List<Department> findAll() {
        return restClient.get()
                .uri("/api/departments")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Department>>() {});
    }

    public Department findById(Long id) {
        return restClient.get()
                .uri("/api/departments/{id}", id)
                .retrieve()
                .body(Department.class);
    }

    public Department update(Long id, DepartmentRequest request) {
        return restClient.put()
                .uri("/api/departments/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(Department.class);
    }

    public void delete(Long id) {
        restClient.delete()
                .uri("/api/departments/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }
}
