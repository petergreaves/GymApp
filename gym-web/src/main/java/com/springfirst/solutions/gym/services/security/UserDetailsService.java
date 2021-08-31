package com.springfirst.solutions.gym.services.security;

import com.springfirst.solutions.gym.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.debug("Getting user details for {}", username);
        return userRepository.findByUsername(username).orElseThrow(() -> {

            return new UsernameNotFoundException("User name not found : " + username);
        });
    }

}
