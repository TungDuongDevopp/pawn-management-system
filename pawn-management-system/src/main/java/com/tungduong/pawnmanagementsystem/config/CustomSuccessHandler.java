package com.tungduong.pawnmanagementsystem.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();

            if (role.equals("ROLE_ADMIN")) {
                response.sendRedirect("/dashboard");
                return;
            }

            if (role.equals("ROLE_STAFF")) {
                response.sendRedirect("/staff");
                return;
            }

            if (role.equals("ROLE_CUSTOMER")) {
                response.sendRedirect("/customer");
                return;
            }
            if (role.equals("ROLE_MANAGER")) {
                response.sendRedirect("/manager");
                return;
            }
        }

        response.sendRedirect("/");
    }
}
