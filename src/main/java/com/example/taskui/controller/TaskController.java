package com.example.taskui.controller;

import com.example.taskui.service.BackendClient;
import com.example.taskui.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class TaskController {

    private final BackendClient backendClient;

    public TaskController(BackendClient backendClient) {
        this.backendClient = backendClient;
    }

    /* =======================
       LIST TASKS
       ======================= */
    @GetMapping("/tasks")
    public String showTasks(Model model, HttpServletRequest request) {

        String jwt = CookieUtil.getJwt(request);
        if (jwt == null) return "redirect:/login";

        model.addAttribute("tasks", backendClient.getTasks(jwt));
        return "tasks";
    }

    /* =======================
       CREATE TASK
       ======================= */
    @GetMapping("/tasks/create")
    public String showCreateTask(HttpServletRequest request) {

        String jwt = CookieUtil.getJwt(request);
        if (jwt == null) return "redirect:/login";

        return "create-task";
    }

    @PostMapping("/tasks/create")
    public String createTask(@RequestParam String title,
                             @RequestParam(required = false) String description,
                             HttpServletRequest request) {

        String jwt = CookieUtil.getJwt(request);
        if (jwt == null) return "redirect:/login";

        backendClient.createTask(jwt, title, description);
        return "redirect:/tasks";
    }

    /* =======================
       EDIT TASK
       ======================= */
    @GetMapping("/tasks/edit/{id}")
    public String showEditTask(@PathVariable Long id,
                               Model model,
                               HttpServletRequest request) {

        String jwt = CookieUtil.getJwt(request);
        if (jwt == null) return "redirect:/login";

        Map<String, Object> task = backendClient.getTaskById(jwt, id);
        model.addAttribute("task", task);

        return "edit-task";
    }

    @PostMapping("/tasks/edit/{id}")
    public String updateTask(@PathVariable Long id,
                             @RequestParam String title,
                             @RequestParam(required = false) String description,
                             HttpServletRequest request) {

        String jwt = CookieUtil.getJwt(request);
        if (jwt == null) return "redirect:/login";

        backendClient.updateTask(jwt, id, title, description);
        return "redirect:/tasks";
    }

    /* =======================
       DELETE TASK
       ======================= */
    @PostMapping("/tasks/delete/{id}")
    public String deleteTask(@PathVariable Long id,
                             HttpServletRequest request) {

        String jwt = CookieUtil.getJwt(request);
        if (jwt == null) return "redirect:/login";

        backendClient.deleteTask(jwt, id);
        return "redirect:/tasks";
    }
}
