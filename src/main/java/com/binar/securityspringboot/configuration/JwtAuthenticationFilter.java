package com.binar.securityspringboot.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // todo -> mendapatkan auth header
        final String authHeader = request.getHeader("Authorization");

        // todo -> variable yang berguna untuk menampung jwt dan username jika ad
        final String jwt, username;

        // todo -> melakukan pengencekan apakah auth header kosong dan autheader diawali dengan header
        if (authHeader == null) {
            System.out.println("MASUK SINI GA MAS");
            filterChain.doFilter(request, response); // todo -> meneruskan request dan response ke filter selanjutnya dalam rantai filter
            return;
        }

        // todo -> <<!! pada line dibawah sini akan dijalankan jika user sudah memiliki authentication !!>>
        System.out.println("MASUK SIINI");

        jwt = authHeader.substring(7); // todo -> berguna untuk mengambil jwtnya saja karena diawal akan di awali dengan bearer dan 1 spasi
        username = jwtService.extractUsername(jwt); // todo -> mendapatkan username dari jwtService.extractUsername yang akan mengembalikan subject atau username

        // todo -> melakukan pengecekan kembali apakah username tidak sama dengan null
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
