package com.roshan.filedigitizationsystem.service;

import com.roshan.filedigitizationsystem.entity.UserInfo;
import com.roshan.filedigitizationsystem.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo users =  userRepo.findByUsername(username);
        if(users==null){
            throw new UsernameNotFoundException(username + " not found");
        }
        return users;
    }
}
