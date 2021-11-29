package io.joework.pictureproviderapi.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import io.joework.pictureproviderapi.domain.Role;
import io.joework.pictureproviderapi.domain.User;
import io.joework.pictureproviderapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    
    private final UserRepository userRepository;

    public User signup(User user){
        user.setRole(Role.USER);
        return userRepository.save(user);
    }
    
}
