package com.cibertec.colitafeliz.JWT;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@WebFilter
public class JwtFilter extends OncePerRequestFilter {


    private JwtUtils jwtUtils;
    private CustomerUsersDetailsService customerUsersDetailsService;
    public JwtFilter(JwtUtils jwtUtils, CustomerUsersDetailsService customerUsersDetailsService) {
        this.jwtUtils = jwtUtils;
        this.customerUsersDetailsService = customerUsersDetailsService;
    }
    private static String userName;
    private static Claims claims;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().matches("/user/login|/user/sign-up|/user/forgot-password")) {
            filterChain.doFilter(request, response);
            return;
        } else {
            String authorizationHeader = request.getHeader("Authorization");
            String jwtToken = null;
            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwtToken = authorizationHeader.substring(7);
                this.userName = jwtUtils.extractUsername(jwtToken);
                this.claims = jwtUtils.extractAllClaims(jwtToken);
            }

            if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = customerUsersDetailsService.loadUserByUsername(userName);
                if(jwtUtils.validateToken(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        }
    }

    public static boolean isAdmin() {
        return "admin".equalsIgnoreCase((String) claims.get("role"));
    }

    public boolean isUser() {
        return "user".equalsIgnoreCase((String) claims.get("role"));
    }
    public static String getCurrentUser() {
        return userName;
    }
}
