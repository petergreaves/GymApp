package com.springfirst.solutions.gym.services.security;

import com.springfirst.solutions.gym.domain.security.Authority;
import com.springfirst.solutions.gym.domain.security.User;
import com.springfirst.solutions.gym.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow(() -> {

            return new UsernameNotFoundException("User name not found : " + username);
        });

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),user.getPassword(),user.getEnabled(),user.getAccountNonExpired(),
                user.getCredentialsNonExpired(), user.getAccountNonLocked(), toSpringAuthorities(user.getAuthorities())

        );
    }

    private Collection< ? extends GrantedAuthority> toSpringAuthorities(Set<Authority> authorities) {

        if (authorities!=null && authorities.size() > 0){

            return authorities.stream()
                    .map(Authority::getRole)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());

        }

        // else no auths
        return new HashSet<>();
    }
}
