package com.davidjdickinson.udacity.ecommerce.controllers;

import java.util.List;
import java.util.Optional;

import com.davidjdickinson.udacity.ecommerce.model.persistence.repositories.CartRepository;
import com.davidjdickinson.udacity.ecommerce.util.LogMF;
import com.davidjdickinson.udacity.ecommerce.util.PasswordValidator;
import com.davidjdickinson.udacity.ecommerce.util.PasswordValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davidjdickinson.udacity.ecommerce.model.persistence.Cart;
import com.davidjdickinson.udacity.ecommerce.model.persistence.User;
import com.davidjdickinson.udacity.ecommerce.model.persistence.repositories.UserRepository;
import com.davidjdickinson.udacity.ecommerce.model.requests.CreateUserRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	PasswordValidator validator = PasswordValidatorFactory.create();

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/id/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		log.debug(LogMF.format("findById","Attempting to find user.", "id", id.toString()));
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) {
			log.debug(LogMF.format("findById", "Invalid user id.", id));
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(user.get());
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<User> findByUserName(@PathVariable String username) throws UsernameNotFoundException {
		log.debug(LogMF.format("findByUserName","Attempting to find user.", "username", username));
		User user = userRepository.findByUsername(username);
		if (user == null) {
			log.debug(LogMF.format("findByUserName", "Username not found.", "username", username));
			return ResponseEntity.badRequest().build();
		}
		log.debug(LogMF.format("findByUserName","Successfully found user.", "username", username));
		return ResponseEntity.ok(user);
	}
	
	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
		log.debug(LogMF.format("createUser","Attempting user create.", createUserRequest));
		// query database is the user exists then return exception message
		User exists = userRepository.findByUsername(createUserRequest.getUsername());

		// does user exist ?
		if (exists != null){
			String userExistsErrorMessage = "Username already exists.";
			log.debug(LogMF.format("createUser", userExistsErrorMessage, createUserRequest));
			return ResponseEntity.badRequest().build();
			// throw new UsernameNotFoundException(userExistsErrorMessage);
		}

		// validate the password here for length, mix of characters and complexity and match of confirmed password
		if (!validator.validate(createUserRequest.getPassword(), createUserRequest.getConfirmPassword())) {
			log.debug(LogMF.format("createUser", validator.getReasonMessage(), createUserRequest));
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
		log.debug(LogMF.format("createUser","User created successfully.", user));
		return ResponseEntity.ok(user);
	}

	@GetMapping("/list")
	public ResponseEntity<List<User>> listUsers() {
		log.debug(LogMF.format("findByUserName","Fetching the user list."));
		return ResponseEntity.ok(userRepository.findAll());
	}
}
