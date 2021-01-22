package com.davidjdickinson.udacity.ecommerce.controllers;

import java.util.List;
import java.util.Optional;

import com.davidjdickinson.udacity.ecommerce.util.LogMF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.davidjdickinson.udacity.ecommerce.model.persistence.Item;
import com.davidjdickinson.udacity.ecommerce.model.persistence.repositories.ItemRepository;

@RestController
@RequestMapping("/api/item")
public class ItemController {

	private static final Logger log = LoggerFactory.getLogger(ItemController.class);

	@Autowired
	private ItemRepository itemRepository;
	
	@GetMapping
	public ResponseEntity<List<Item>> getItems() {
		return ResponseEntity.ok(itemRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Item> getItemById(@PathVariable Long id) {
		log.debug(LogMF.format("getItemById", "Attempting to find item by id.", "Id", id.toString()));
		Optional<Item> item = itemRepository.findById(id);
		if (item.isEmpty()){
			log.debug(LogMF.format("getItemById", "Invalid item id.", "Id", id.toString()));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		log.debug(LogMF.format("getItemById", "Success: item found.", item.get()));
		return ResponseEntity.ok(item.get());
	}
	
	@GetMapping("/name")
	public ResponseEntity<List<Item>> getItemsByName(@RequestParam String name) {
		log.debug(LogMF.format("getItemByName", "Attempting to find item by name.", "Item name", name));
		List<Item> items = itemRepository.findByName(name);
		if (items.isEmpty()) {
			log.debug(LogMF.format("getItemByName", "No items found.", "Item name", name));
			return ResponseEntity.notFound().build();
		} else {
			log.debug(LogMF.format("getItemByName", "Success: item found.", items));
			return ResponseEntity.ok(items);
		}
	}
	
}
