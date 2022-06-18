package com.gfg.BlogAppApis.controllers;

import com.gfg.BlogAppApis.exceptions.ApiException;
import com.gfg.BlogAppApis.payloads.JwtAuthRequest;
import com.gfg.BlogAppApis.payloads.JwtAuthResponse;
import com.gfg.BlogAppApis.payloads.UserDto;
import com.gfg.BlogAppApis.security.JwtTokenHelper;
import com.gfg.BlogAppApis.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
        authenticate(request.getUsername(),request.getPassword());
        UserDetails userDetails=userDetailsService.loadUserByUsername(request.getUsername());
        String token=jwtTokenHelper.generateToken(userDetails);
        JwtAuthResponse response=new JwtAuthResponse();
        response.setToken(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username,password);
        try{
            authenticationManager.authenticate(authenticationToken);
        }
        catch(BadCredentialsException e){
            System.out.println("Invalid Details!!");
            throw new ApiException("Invalid username or password");
        }
    }
    @PostMapping("/register")
    public ResponseEntity<UserDto> reqisterUser(@RequestBody UserDto userDto){
        UserDto registeredUser=userService.registerNewUser(userDto);
        return new ResponseEntity<>(registeredUser,HttpStatus.CREATED);
    }

}
