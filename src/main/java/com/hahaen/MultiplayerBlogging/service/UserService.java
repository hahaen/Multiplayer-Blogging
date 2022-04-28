package com.hahaen.MultiplayerBlogging.service;

import com.hahaen.MultiplayerBlogging.entity.User;
import com.hahaen.MultiplayerBlogging.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService implements UserDetailsService {
    private final Map<String, String> userPasswords = new ConcurrentHashMap<>();

    private UserMapper userMapper;

    @Inject
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public UserService() {
        userPasswords.put("admin", "123456");
    }

    public void save(String username, String password) {
        userPasswords.put(username, password);
    }

    public String getPassword(String username) {
        return userPasswords.get(username);
    }

    public User getUserById(Integer id) {
        return userMapper.findUserById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userPasswords.containsKey(username)) {
            throw new UsernameNotFoundException(username + "：用户不存在！");
        }

        String password = userPasswords.get(username);

        return new org.springframework.security.core.userdetails.User(
                username, password, Collections.emptyList());
    }
}
