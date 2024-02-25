package com.jwtauthentication.jwt.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import org.springframework.stereotype.Component;

import com.jwtauthentication.jwt.constants.SecurityConstant;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomSucessAuthHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        List<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals(SecurityConstant.ROLE_ADMIN)) {
                response.sendRedirect("/adminPage");
                return;
            }
            response.sendRedirect("/userPage");
        }

    }

}
