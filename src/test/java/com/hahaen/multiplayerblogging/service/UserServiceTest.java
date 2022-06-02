package com.hahaen.multiplayerblogging.service;

import com.hahaen.multiplayerblogging.entity.User;
import com.hahaen.multiplayerblogging.dao.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    BCryptPasswordEncoder mockEncoder;
    @Mock
    UserMapper mockUserMapper;
    @InjectMocks
    UserService userService;

    @Test
    void testSave() {
        when(mockEncoder.encode("myPassword")).thenReturn("myEncodePassword");
        userService.save("myUsername", "myPassword");
        Mockito.verify(mockUserMapper).save("myUsername", "myEncodePassword");
    }

    @Test
    void testGetUserByUserName() {
        userService.getUserByUserName("myUsername");
        Mockito.verify(mockUserMapper).findUserByUsername("myUsername");
    }

    @Test
    void throwExceptionWhenUserNotFound() {
        assertThrows(UsernameNotFoundException.class,
                () -> userService.loadUserByUsername("myUsername"));
    }

    @Test
    void returnUserDetailsWhenUserNotFound() {
        when(mockUserMapper.findUserByUsername("myUsername"))
                .thenReturn(new User(123, "myUsername", "myEncodePassword"));

        UserDetails userDetails = userService.loadUserByUsername("myUsername");

        assertEquals("myUsername", userDetails.getUsername());
        assertEquals("myEncodePassword", userDetails.getPassword());
    }
}