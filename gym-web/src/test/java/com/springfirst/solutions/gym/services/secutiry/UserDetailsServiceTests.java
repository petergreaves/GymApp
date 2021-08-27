package com.springfirst.solutions.gym.services.secutiry;

import com.springfirst.solutions.gym.domain.security.Authority;
import com.springfirst.solutions.gym.domain.security.Role;
import com.springfirst.solutions.gym.repositories.security.UserRepository;
import com.springfirst.solutions.gym.services.security.UserDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Disabled
public class UserDetailsServiceTests {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    private UserDetailsService userDetailsService;

    private UserDetails userExpectedFromService;
    private com.springfirst.solutions.gym.domain.security.User userFromRepository;

    @BeforeEach
    public void setup() {

        userExpectedFromService = new User("foo@bar.com",
                "password", true, true,
                true, true, Set.of(new SimpleGrantedAuthority("MEMBER")));

        userFromRepository = com.springfirst.solutions.gym.domain.security.User.builder()
                .username("foo@bar.com")
                .password("password")
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .accountNonLocked(true)
             //   .role(Role.builder().roleName("MEMBER").authorities()build())
                .enabled(true)
                .build();

    }

//    @Test
//    public void testUserLoad() {
//
//        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(userFromRepository));
//        userExpectedFromService = userDetailsService.loadUserByUsername("foo@bar.com");
//
//        Assertions.assertAll(
//                () -> {
//                    Assertions.assertEquals(userExpectedFromService.getUsername(), userFromRepository.getUsername());
//                },
//                () -> {
//                    Assertions.assertEquals(userExpectedFromService.getPassword(), userFromRepository.getPassword());
//                },
//                () -> {
//                    Assertions.assertEquals(
//                            userExpectedFromService
//                                    .getAuthorities()
//                                    .stream()
//                                    .map(grantedAuthority -> {return grantedAuthority.getAuthority().toUpperCase(Locale.ROOT);})
//                                    .collect(Collectors.toList()),
//                            userFromRepository
//                                    .getAuthorities()
//                                    .stream()
//                                    .map(auth -> {return auth.getRole();})
//                                    .collect(Collectors.toList()));
//                },
//                () -> {
//                    Assertions.assertEquals(userExpectedFromService.isAccountNonExpired(), userFromRepository.getAccountNonExpired());
//                },
//                () -> {
//                    Assertions.assertEquals(userExpectedFromService.isAccountNonLocked(), userFromRepository.getAccountNonLocked());
//                },
//                () -> {
//                    Assertions.assertEquals(userExpectedFromService.isCredentialsNonExpired(), userFromRepository.getCredentialsNonExpired());
//                }
//        );
//
//    }

}
