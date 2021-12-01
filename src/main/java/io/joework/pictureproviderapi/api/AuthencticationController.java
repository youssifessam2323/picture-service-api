package io.joework.pictureproviderapi.api;



import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.joework.pictureproviderapi.domain.User;
import io.joework.pictureproviderapi.service.AuthService;
import io.joework.pictureproviderapi.util.JwtUtils;
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
        

    @PostMapping("signin")
    public ResponseEntity<?> signin(@Valid @RequestBody User request){
        
        log.info("enter the sign in endpoint...");
        
        User authUser = authService.signin(request);
        
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION
                    , JwtUtils.generateToken(authUser)
                    ).body(authUser);
    }    
    
  
}
