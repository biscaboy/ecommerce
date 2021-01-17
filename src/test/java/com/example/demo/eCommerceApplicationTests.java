package com.example.demo;

import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.CreateUserRequest;
import com.example.demo.security.LoginRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class eCommerceApplicationTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<CreateUserRequest> createJson;

    @Autowired
    private JacksonTester<User> userJson;

    @Autowired
    private JacksonTester<LoginRequest> loginJson;

    @Autowired
    private JacksonTester<ArrayList<User>> userListJson;

    @Test
    public void create_new_user() throws Exception {

        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setUsername("BillyHoliday");
        userRequest.setPassword("789yuiYUI&*(");
        userRequest.setConfirmPassword("789yuiYUI&*(");

        mvc.perform(post(new URI("/api/user/create"))
                .content(createJson.write(userRequest).getJson())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("username").isNotEmpty())
                .andExpect(jsonPath("username").value(userRequest.getUsername()))
                .andExpect(jsonPath("id").isNotEmpty());
    }

    @Test
    public void get_user_list_authorized() throws Exception {

        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setUsername("BullDurham");
        userRequest.setPassword("*)*JJJjkj2343)");
        userRequest.setConfirmPassword("*)*JJJjkj2343)");

        LoginRequest loginRequest = new LoginRequest();
        BeanUtils.copyProperties(userRequest, loginRequest);

        mvc.perform(post(new URI("/api/user/create"))
                .content(createJson.write(userRequest).getJson())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        String token = mvc.perform(post(new URI("/login"))
                .content(loginJson.write(loginRequest).getJson())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getHeader("Authorization");

        String responseContent = mvc.perform(
                get(new URI("/api/user/list"))
                        .content(createJson.write(userRequest).getJson())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ArrayList<User> users = userListJson.parse(responseContent).getObject();
        assertEquals(userRequest.getUsername(), users.get(users.size() - 1).getUsername());
    }
}
