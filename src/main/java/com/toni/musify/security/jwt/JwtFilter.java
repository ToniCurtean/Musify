package com.toni.musify.security.jwt;

import com.toni.musify.domain.user.model.User;
import com.toni.musify.domain.user.model.UserCredentials;
import com.toni.musify.domain.user.model.UserRole;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.toni.musify.security.jwt.JwtService.ClaimsEnum.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;


    /*
    @Override
    @SneakyThrows
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader=request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if(requestTokenHeader==null || !requestTokenHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        Claims claims=null;
        jwt=requestTokenHeader.substring(7);
        try{
            claims= jwtService.getClaims(jwt);
        }catch (Exception e){
            log.trace(e.getMessage());
        }
        userEmail=jwtService.extractUsername(jwt);

        if(nonNull(userEmail) && isNull(SecurityContextHolder.getContext().getAuthentication())){
            //UserDetails userDetails= userDetailsService.loadUserByUsername(userEmail);
            UserCredentials userCredentials=new UserCredentials(
                    (Integer) claims.get(ID.name()),
                    (String) claims.get(EMAIL.name())
            );
            log.info(userCredentials.email()+" "+userCredentials.id());
            if(jwtService.isTokenValid(jwt,userCredentials)){
                UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userCredentials,null,null);
                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }
     */

    @Override
    @SneakyThrows
    protected void doFilterInternal(@Nonnull HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain) {
        final String requestTokenHeader = request.getHeader("Authorization");

        Claims claims = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            String jwtToken = requestTokenHeader.substring(7);

            try {
                claims = jwtService.getClaims(jwtToken);
            } catch (Exception e) {
                log.trace(e.getMessage());
            }
        }

        if (nonNull(claims) && isNull(SecurityContextHolder.getContext().getAuthentication())) {
            UserCredentials userCredentials = new UserCredentials(
                    (Integer) claims.get(ID.name()),
                    (String) claims.get(EMAIL.name()),
                    (String) claims.get(ROLE.name())
                    );
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userCredentials, null, null);
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

}
