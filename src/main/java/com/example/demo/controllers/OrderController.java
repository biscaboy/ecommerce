package com.example.demo.controllers;

import java.util.List;

import com.example.demo.util.LogMF;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	private static final Logger log = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	
	@PostMapping("/submit/{username}")
	public ResponseEntity<UserOrder> submit(@PathVariable String username) {
		log.debug(LogMF.format("submit", "Attempting create order.", "username", username));
		User user = userRepository.findByUsername(username);
		if(user == null) {
			log.debug(LogMF.format("submit", "Invalid username.", "username", username));
			return ResponseEntity.notFound().build();
		}
		UserOrder order = UserOrder.createFromCart(user.getCart());
		orderRepository.save(order);
		log.debug(LogMF.format("submit", "Success: Order created.", order));
		return ResponseEntity.ok(order);
	}
	
	@GetMapping("/history/{username}")
	public ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String username) {
		log.debug(LogMF.format("getOrdersForUser", "Attempting to get order.", "username", username));
		User user = userRepository.findByUsername(username);
		if(user == null) {
			log.debug(LogMF.format("getOrdersForUser", "Invalid user id.", "username", username));
			return ResponseEntity.notFound().build();
		}
		List<UserOrder> orders = orderRepository.findByUser(user);
		if (orders.isEmpty()) {
			log.debug(LogMF.format("getOrdersForUser", "No orders found.", orders));
		} else {
			log.debug(LogMF.format("getOrdersForUser", "Success: orders found.", orders));
		}
		return ResponseEntity.ok(orders);
	}
}
