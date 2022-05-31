package com.hahaen.MultiplayerBlogging.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hahaen.MultiplayerBlogging.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class AuthControllerTest {
    private MockMvc mockMvc;

    @Mock
    private UserService userService;
    @Mock
    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new AuthController(userService, authenticationManager)).build();
    }

    @Test
    void returnNotLoginByDefault() throws Exception {
        mockMvc.perform(get("/auth")).andExpect(status().isOk()).andExpect(mvcResult -> {
            Assertions.assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("用户没有登录"));
        });
    }

    @Test
    void testLogin() throws Exception {
        //未登录，检查/auth返回值
        mockMvc.perform(get("/auth")).andExpect(status().isOk()).andExpect(mvcResult -> {
            System.out.println(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
            Assertions.assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("用户没有登录"));
        });

        //登录
        Map<String, String> usernamePassword = new HashMap<>();
        usernamePassword.put("username", "myUsername");
        usernamePassword.put("password", "myPassword");

        when(userService.loadUserByUsername("myUsername"))
                .thenReturn(new User("myUsername"
                        , bCryptPasswordEncoder.encode("myPassword")
                        , Collections.emptyList()));
        when(userService.getUserByUserName("myUsername"))
                .thenReturn(new com.hahaen.MultiplayerBlogging.entity.User(123, "myUsername"
                        , bCryptPasswordEncoder.encode("myPassword")));

        MvcResult response = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new ObjectMapper().writeValueAsString(usernamePassword)))
                .andExpect(status().isOk())
                .andExpect(result -> Assertions.assertTrue(result.getResponse()
                        .getContentAsString(StandardCharsets.UTF_8)
                        .contains("登录成功")))
                .andReturn();

        HttpSession session = response.getRequest().getSession();

        //处于登录状态，再次检查/auth返回值
        mockMvc.perform(get("/auth").session((MockHttpSession) session)).andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    System.out.println(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
                    Assertions.assertTrue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8).contains("myUsername"));
                });
    }
}
