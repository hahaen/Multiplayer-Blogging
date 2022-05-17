package com.hahaen.MultiplayerBlogging.service;

import com.hahaen.MultiplayerBlogging.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService implements UserDetailsService {

    private Map<String, User> users = new ConcurrentHashMap<>();

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Inject
    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        save("admin", "123456");
    }

    public void save(String username, String password) {
        users.put(username,
                new User(1, username, bCryptPasswordEncoder.encode(password)));
    }

//    public User getUserById(Integer id) {
//        return null;
//    }

    public User getUserByUserName(String username) {
        return users.get(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!users.containsKey(username)) {
            throw new UsernameNotFoundException(username + "：用户不存在！");
        }

        User user = users.get(username);

        return new org.springframework.security.core.userdetails.User(
                username, user.getEncryptedPassword(), Collections.emptyList());
    }
}
