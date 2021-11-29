package io.joework.pictureproviderapi.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.joework.pictureproviderapi.domain.User;
import io.joework.pictureproviderapi.repository.UserRepository;
import io.joework.pictureproviderapi.util.JwtUtils;
import lombok.RequiredArgsConstructor;

@Component  
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter{

    private final UserRepository userRepository;
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        
        String header = request.getHeader(AUTHORIZATION_HEADER);

        if(header == null ) {
            chain.doFilter(request,response);
            return ;
        }

        if(header.isEmpty() || !header.startsWith("Bearer ")){
            chain.doFilter(request, response);  
            return;
        }

        String token = header.split(" ")[1].trim();

        User user = userRepository
                        .findByUsername(JwtUtils.getUsername(token))
                        .orElseThrow(() -> new UsernameNotFoundException("invalid data from the token"));

        UsernamePasswordAuthenticationToken authentication =
                     new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }

}
    
