package com.example.hello_spring.config;
import com.example.hello_spring.model.response.ErrorMessage;
import com.example.hello_spring.service.JwtService;
import com.example.hello_spring.utility.JwtUtility;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private  JwtUtility jwtUtility;
   @Autowired private  JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader("Authorization");

       final ErrorMessage errorMessage = new ErrorMessage();
       final Logger logger= LoggerFactory.getLogger(JwtFilter.class);
        String jwtToken = null;
        String userName = null;

        if (header != null && header.startsWith("Bearer ")) {
            jwtToken = header.substring(7);

            try {
                userName = this.jwtUtility.getUserNameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (ExpiredJwtException e) {

                errorMessage.setErrorMessage("Expired token");
                errorMessage.setStatusCode(HttpStatus.UNAUTHORIZED);
                logger.error("expired token, need refresh token");

                String isRefreshToken = request.getHeader("isRefreshToken");
                String requestURL = request.getRequestURL().toString();
                // allow for Refresh Token creation if following conditions are true.
                if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("refreshToken")) {
                    allowForRefreshToken(e, request);
                } else
                    request.setAttribute("exception", e);
            }catch (Exception e){
              log.error("error",e.getMessage());
            }
        } else {
            System.out.println("Jwt token doesn't start with Bearer");
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.jwtService.loadUserByUsername(userName);

            if (jwtUtility.validateToken(jwtToken, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);

    }


    private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                null, null, null);
        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        // Set the claims so that in controller we will be using it to create
        // new JWT
        request.setAttribute("claims", ex.getClaims());

    }


}

