package com.example.taskui.controller;

import com.example.taskui.service.BackendClient;
import com.example.taskui.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private final BackendClient backendClient;

    public LoginController(BackendClient backendClient) {
        this.backendClient = backendClient;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpServletResponse response) {

        String jwt = backendClient.login(username, password);
        Cookie jwtCookie = CookieUtil.createJwtCookie(jwt);
        response.addCookie(jwtCookie);
        return "redirect:/tasks";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("JWT_TOKEN", "");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(0); // delete
        response.addCookie(cookie);
        Cookie jSessionCookie = new Cookie("JSESSIONID", "");
        jSessionCookie.setPath("/");
        jSessionCookie.setHttpOnly(true);
        jSessionCookie.setSecure(true);
        jSessionCookie.setMaxAge(0); // delete
        response.addCookie(jSessionCookie);
        return "redirect:/login?logout";
    }
}
