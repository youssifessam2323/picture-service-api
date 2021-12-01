package io.joework.pictureproviderapi.service;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import io.joework.pictureproviderapi.domain.Role;
import io.joework.pictureproviderapi.domain.User;
import io.joework.pictureproviderapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    public User signup(User user){
        user.setRole(Role.USER);
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User signin(@RequestBody @Valid User request) {
        var authentication = authenticationManager
        .authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()
                ));
        
        User user = (User) authentication.getPrincipal();
        return user;
    }
    
}
