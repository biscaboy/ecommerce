package com.example.demo.controllers;

import java.util.List;

import com.example.demo.security.JWTTokenFactory;
import com.example.demo.security.SecurityConstants;
import com.example.demo.util.LogMF;
import com.example.demo.util.PasswordValidator;
import com.example.demo.util.PasswordValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@RestController
@RequestMapping("/api/user")
public class UserController {

	Logger log = LoggerFactory.getLogger(UserController.class);
	PasswordValidator validator = PasswordValidatorFactory.create();

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/login")
	public void login() {

	}

	@GetMapping("/id/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		log.info(LogMF.format("findById","Attempting to find user.", "id", id.toString()));
		return ResponseEntity.of(userRepository.findById(id));
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<User> findByUserName(@PathVariable String username) throws UsernameNotFoundException {
		log.info(LogMF.format("findByUserName","Attempting to find user.", "username", username));
		User user = userRepository.findByUsername(username);
		if (user == null) {
			log.error(LogMF.format("findByUserName", "Username not found.", "username", username));
			return ResponseEntity.badRequest().build();
		}
		log.info(LogMF.format("findByUserName","Successfully found user.", "username", username));
		return ResponseEntity.ok(user);
	}
	
	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
		log.info(LogMF.format("createUser","Attempting user create.", createUserRequest));
		// query database is the user exists then return exception message
		User exists = userRepository.findByUsername(createUserRequest.getUsername());

		// does user exist ?
		if (exists != null){
			String userExistsErrorMessage = "Username already exists.";
			log.error(LogMF.format("createUser", userExistsErrorMessage, createUserRequest));
			return ResponseEntity.badRequest().build();
			// throw new UsernameNotFoundException(userExistsErrorMessage);
		}

		// validate the password here for length, mix of characters and complexity and match of confirmed password
		if (!validator.validate(createUserRequest.getPassword(), createUserRequest.getConfirmPassword())) {
			log.error(LogMF.format("createUser", validator.getReasonMessage(), createUserRequest));
			return ResponseEntity.badRequest().build();
		}
		User user = new User();
		user.setUsername(createUserRequest.getUsername());
		// Note:  The salt is saved as part of the bCrypt password.
		// See: https://stackoverflow.com/questions/6832445/how-can-bcrypt-have-built-in-salts
		user.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));

		Cart cart = new Cart();
		cartRepository.save(cart);
		user.setCart(cart);
		userRepository.save(user);
		log.info(LogMF.format("createUser","User created successfully.", user));
		return ResponseEntity.ok(user);
	}

	@GetMapping("/list")
	public ResponseEntity<List<User>> listUsers() {
		log.info(LogMF.format("findByUserName","Fetching the user list."));
		return ResponseEntity.ok(userRepository.findAll());
	}
}
