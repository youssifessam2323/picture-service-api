package io.joework.pictureproviderapi.api;


import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.joework.pictureproviderapi.domain.User;
import io.joework.pictureproviderapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Slf4j
public class AuthencticationController {

    private final AuthService authService;

    @PostMapping("signup")
    public ResponseEntity<?> signup(@Valid @RequestBody  User user){
        log.info("enter the signup endpoint...");
        return ResponseEntity.status(HttpStatus.OK).body(authService.signup(user));
    }
        

    
    
  
}
