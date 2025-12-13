package com.example.taskui.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BackendClient {

    @Value("${backend.base-url}")
    private String backendUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public String login(String username, String password) {

        Map<String, String> body = new HashMap<>();
        body.put("username", username);
        body.put( "password", password);

        ResponseEntity<Map> response =
                restTemplate.postForEntity(
                        backendUrl + "/auth/login",
                        body,
                        Map.class
                );

        return (String) response.getBody().get("token");
    }

    public List<Map<String, Object>> getTasks(String jwt) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwt);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<List> response =
                restTemplate.exchange(
                        backendUrl + "/api/tasks",
                        HttpMethod.GET,
                        entity,
                        List.class
                );

        return response.getBody();
    }

    public void createTask(String jwt, String title, String description, String status, String assignedTo) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwt);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = new HashMap<>();
        body.put("title", title);
        body.put("description", description);
        body.put("status", status);
        body.put("assignedTo", assignedTo);

        HttpEntity<Map<String, String>> entity =
                new HttpEntity<>(body, headers);

        restTemplate.postForEntity(
                backendUrl + "/api/tasks",
                entity,
                Void.class
        );
    }

    public Map<String, Object> getTaskById(String jwt, Long id) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwt);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response =
                restTemplate.exchange(
                        backendUrl + "/api/tasks/" + id,
                        HttpMethod.GET,
                        entity,
                        Map.class
                );

        return response.getBody();
    }

    public void updateTask(String jwt, Long id, String title, String description, String status, String assignedTo) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwt);
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> body = new HashMap<>();
        body.put("title", title);
        body.put("description", description);
        body.put("status", status);
        body.put("assignedTo", assignedTo);
        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(body, headers);
        restTemplate.exchange(
                backendUrl + "/api/tasks/" + id,
                HttpMethod.PUT,
                entity,
                Void.class
        );
    }

    public void deleteTask(String jwt, Long id) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwt);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        restTemplate.exchange(
                backendUrl + "/api/tasks/" + id,
                HttpMethod.DELETE,
                entity,
                Void.class
        );
    }

}
