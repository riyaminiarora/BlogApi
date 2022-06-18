package com.gfg.BlogAppApis.security;

import com.gfg.BlogAppApis.entities.User;
import com.gfg.BlogAppApis.exceptions.ResourceNotFoundException;
import com.gfg.BlogAppApis.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //load user from db by username(userRepo needed)
        User user=userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User"," email "+username,0));//email is treated as username
        return user;
    }
}
