package com.davidjdickinson.udacity.ecommerce.errors;

import com.davidjdickinson.udacity.ecommerce.controllers.UserController;
import com.davidjdickinson.udacity.ecommerce.model.persistence.User;
import com.davidjdickinson.udacity.ecommerce.model.requests.CreateUserRequest;
import com.davidjdickinson.udacity.ecommerce.security.LoginRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ErrorControllerTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<CreateUserRequest> createJson;

    @Autowired
    private JacksonTester<LoginRequest> loginJson;

    @Autowired JacksonTester<ApiError> apiErrorJson;

    private String token;

    @Before
    public void setup() {
        MockMvc mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setUsername("BestUserEver");
        userRequest.setPassword("123!@#ASAasa");
        userRequest.setConfirmPassword("123!@#ASAasa");

        LoginRequest loginRequest = new LoginRequest();
        BeanUtils.copyProperties(userRequest, loginRequest);

        try {
            mvc.perform(post(new URI("/api/user/create"))
                    .content(createJson.write(userRequest).getJson())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8));

            this.token = mvc.perform(post(new URI("/login"))
                    .content(loginJson.write(loginRequest).getJson())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getHeader("Authorization");

        } catch (Exception e) {
            throw new RuntimeException(e.getCause());
        }
    }

    @Test
    public void test_exception_user_not_found() {
        try {
            mvc.perform(get(new URI("/api/user/NotFoundUserGuy"))
                    .header("Authorization", token)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void test_exeception_password_validation() {
        try {
            CreateUserRequest userRequest = new CreateUserRequest();
            userRequest.setUsername("WorstUserEver");
            userRequest.setPassword("invalidpassword");
            userRequest.setConfirmPassword("invalidpassword");
            String content = mvc.perform(post(new URI("/api/user/create"))
                    .content(createJson.write(userRequest).getJson())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isBadRequest())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            ApiError error = apiErrorJson.parse(content).getObject();
            assertTrue(error.getMessage().startsWith("User create failed."));

        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void test_exeception_username_exists() {
        try {
            CreateUserRequest userRequest = new CreateUserRequest();
            userRequest.setUsername("BestUserEver");
            userRequest.setPassword("asdASD123!@#");
            userRequest.setConfirmPassword("asdASD123!@#");
            String content = mvc.perform(post(new URI("/api/user/create"))
                    .content(createJson.write(userRequest).getJson())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isBadRequest())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            ApiError error = apiErrorJson.parse(content).getObject();
            assertTrue(error.getMessage().startsWith("User create failed."));

        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void test_exeception_invalid_user_id() {
        try {
            String content = mvc.perform(get(new URI("/api/user/id/1231"))
                    .header("Authorization", token)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isBadRequest())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            ApiError error = apiErrorJson.parse(content).getObject();
            assertTrue(error.getMessage().startsWith("Invalid user id."));

        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void test_empty_validation() {
        try {
            CreateUserRequest userRequest = new CreateUserRequest();
            userRequest.setUsername("BestUserEver");
            userRequest.setPassword("asdASD123!@#");
            String content = mvc.perform(post(new URI("/api/user/create"))
                    .content(createJson.write(userRequest).getJson())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(status().isBadRequest())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            ApiError error = apiErrorJson.parse(content).getObject();
            assertTrue(error.getMessage().startsWith("Validation failed."));

        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

}
