package com.example.taskui.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {

    public static String getJwt(HttpServletRequest request) {
        if (request.getCookies() == null) return null;

        for (Cookie cookie : request.getCookies()) {
            if ("JWT_TOKEN".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public static Cookie createJwtCookie(String jwt) {
        Cookie jwtCookie = new Cookie("JWT_TOKEN", jwt);
        jwtCookie.setHttpOnly(true);   // ✅ KEY POINT
        jwtCookie.setSecure(true);     // HTTPS only
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(60 * 60);  // 1 hour
        return jwtCookie;
    }
}