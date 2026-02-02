package com.itransform.auth_service.service;

import com.itransform.auth_service.entity.User;
import com.itransform.auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private  UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not found with email: " + email));
        UserBuilder userBuilder= org.springframework.security.core.userdetails.User.withUsername(user.getEmail());
        userBuilder.password(user.getPassword());
        userBuilder.authorities(new SimpleGrantedAuthority(user.getRole()));
        return userBuilder.build();

    }
}
