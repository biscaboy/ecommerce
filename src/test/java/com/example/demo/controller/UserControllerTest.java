package com.example.demo.controller;

import com.example.demo.TestUtils;
import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void init() {
        // inject
        this.userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", encoder);
    }

    @Test
    @DisplayName("Create user")
    public void create_user_happy_path() throws Exception {
            String username = "test";
        String password = "BoogieNights3838";
        String hashedPassword = "this*is*a*hashed*password";

       // stubbing example
        when(encoder.encode(password)).thenReturn(hashedPassword);
        when(userRepository.findByUsername(username)).thenReturn(null);

        CreateUserRequest request = new CreateUserRequest();
        request.setUsername(username);
        request.setPassword(password);
        request.setConfirmPassword(password);

        final ResponseEntity<User> response = userController.createUser(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        User user = response.getBody();
        assertNotNull(user);
        assertEquals(0, user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(hashedPassword, user.getPassword());

    }

    @Test
    @DisplayName("User password is too short")
    public void create_user_short_password() {
        String username = "test";
        String password = "oops";
        String confirmPassword = "confirm";
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername(username);
        request.setPassword(password);
        request.setConfirmPassword(password);
        ResponseEntity<User> response = userController.createUser(request);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    @DisplayName ("Confirming password does not match the password")
    public void create_user_confirm_password_not_matching_password() {
        String username = "test";
        String password = "LongEnough";
        String confirmPassword = "oops!";
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername(username);
        request.setPassword(password);
        request.setConfirmPassword(confirmPassword);
        ResponseEntity<User> response = userController.createUser(request);
        assertEquals(400, response.getStatusCodeValue());

    }

    @Test
    @DisplayName("Get all users")
    public void get_all_users() throws Exception {
        String username = "test";
        String password = "passwordIsLong";

        // stubbing example
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        CreateUserRequest request = new CreateUserRequest();
        request.setUsername(username);
        request.setPassword(password);
        request.setConfirmPassword(password);

        ResponseEntity<List<User>> response = userController.listUsers();
        assertEquals(200, response.getStatusCodeValue());
    }


    @Test
    @DisplayName("Get all user by username")
    public void get_user_by_username() throws Exception {
        String username = "test";
        String password = "passwordIsLong";
        User user1 = new User();

        // stubbing example
        when(userRepository.findByUsername(username)).thenReturn(user1);

        CreateUserRequest request = new CreateUserRequest();
        request.setUsername(username);
        request.setPassword(password);
        request.setConfirmPassword(password);

        ResponseEntity<User> response = userController.findByUserName(username);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("Get all user by username")
    public void get_user_by_id() throws Exception {
        String username = "test";
        String password = "passwordIsLong";
        User user1 = new User();

        // stubbing example
        when(userRepository.findById(0L)).thenReturn(java.util.Optional.of(user1));

        CreateUserRequest request = new CreateUserRequest();
        request.setUsername(username);
        request.setPassword(password);
        request.setConfirmPassword(password);

        ResponseEntity<User> response = userController.findById(0L);
        assertEquals(200, response.getStatusCodeValue());
    }
}
