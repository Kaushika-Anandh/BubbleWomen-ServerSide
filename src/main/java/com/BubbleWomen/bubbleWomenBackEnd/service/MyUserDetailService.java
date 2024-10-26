package com.BubbleWomen.bubbleWomenBackEnd.service;

import com.BubbleWomen.bubbleWomenBackEnd.model.UserPrincipal;
import com.BubbleWomen.bubbleWomenBackEnd.model.Users;
import com.BubbleWomen.bubbleWomenBackEnd.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        Users users = userRepository.findByUsername(username);
        if (users == null){
            throw new UsernameNotFoundException("user not found");
        }
        return new UserPrincipal(users);
    }
}
