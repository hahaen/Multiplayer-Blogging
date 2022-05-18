package com.hahaen.MultiplayerBlogging.service;

import com.hahaen.MultiplayerBlogging.entity.User;
import com.hahaen.MultiplayerBlogging.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    private UserMapper userMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Inject
    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder,
                       UserMapper userMapper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userMapper = userMapper;
//        save("admin", "123456");
    }

    public void save(String username, String password) {
        userMapper.save(username, bCryptPasswordEncoder.encode(password));
    }


    public User getUserByUserName(String username) {
        return userMapper.findUserByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + "：用户不存在！");
        }

        return new org.springframework.security.core.userdetails.User(
                username, user.getEncryptedPassword(), Collections.emptyList());
    }
}
