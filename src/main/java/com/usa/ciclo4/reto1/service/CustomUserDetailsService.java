package com.usa.ciclo4.reto1.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.usa.ciclo4.reto1.model.User;
import com.usa.ciclo4.reto1.repository.UserRepository;
import com.usa.ciclo4.reto1.security.CustomUserDetails;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepo.buscarPorEmail(email);
        if (user.isEmpty()){
            throw new UsernameNotFoundException("Usuario no encontrado, email = " + email);
        }

        return new CustomUserDetails(user.get());
    }
    
}