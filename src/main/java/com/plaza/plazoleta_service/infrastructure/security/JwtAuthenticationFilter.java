package com.plaza.plazoleta_service.infrastructure.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7); // quitar "Bearer "

            if (jwtUtil.validateToken(token)) {
                String correo = jwtUtil.getCorreoFromToken(token);
                String rol = jwtUtil.getRolFromToken(token); // Ej: ROLE_CLIENTE

                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(rol));

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(correo, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authToken);

                System.out.println("Usuario autenticado: " + correo + " con rol: " + rol);
            }
        }

        // Continuar con la cadena de filtros
        filterChain.doFilter(request, response);
    }
}

