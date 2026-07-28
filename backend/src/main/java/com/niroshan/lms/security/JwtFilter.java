package com.niroshan.lms.security;


import jakarta.servlet.*;
import jakarta.servlet.http.*;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;



@Component
public class JwtFilter extends OncePerRequestFilter {



    private final JwtUtil jwtUtil;

    private final UserDetailsService userDetailsService;



    public JwtFilter(
            JwtUtil jwtUtil,
            UserDetailsService userDetailsService
    ){

        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;

    }



    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    )
            throws ServletException, IOException {



        String header =
                request.getHeader("Authorization");



        String email = null;

        String token = null;



        if(header != null &&
                header.startsWith("Bearer ")) {


            token = header.substring(7);


            try {

                email =
                        jwtUtil.extractEmail(token);


            } catch(Exception e){

                System.out.println("Invalid JWT");

            }

        }



        if(email != null &&
                SecurityContextHolder
                        .getContext()
                        .getAuthentication() == null){



            UserDetails userDetails =
                    userDetailsService
                            .loadUserByUsername(email);



            if(jwtUtil.isValid(
                    token,
                    userDetails
            )){


                UsernamePasswordAuthenticationToken authentication =


                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );



                authentication.setDetails(

                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)

                );



                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);


            }

        }



        filterChain.doFilter(
                request,
                response
        );

    }

}