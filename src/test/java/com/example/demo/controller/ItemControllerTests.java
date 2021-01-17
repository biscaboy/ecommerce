package com.example.demo.controller;

import com.example.demo.TestUtils;
import com.example.demo.controllers.CartController;
import com.example.demo.controllers.ItemController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTests {

    private ItemController itemController;
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @BeforeEach
    public void beforeEach() {
        // inject
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepository);
    }

    // get a list of items
    @DisplayName("Get a list of items")
    @Test
    public void get_a_list_of_items(){
        List items = new ArrayList<Item>();
        items.add(new Item());
        when(itemRepository.findAll()).thenReturn(items);
        ResponseEntity<List<Item>> response = itemController.getItems();
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        List<Item> returned = response.getBody();
        assertNotNull(returned);
    }
    // get an idem by id
    @DisplayName("Get an item by id")
    @Test
    public void get_item_by_id(){
        long id = 1;
        Item item = new Item();

        when(itemRepository.findById(id)).thenReturn(Optional.ofNullable(item));

        ResponseEntity<Item> response = itemController.getItemById(id);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        Item returned = response.getBody();
        assertNotNull(returned);
    }

    // get an item by name
    @DisplayName("Get an item by name")
    @Test
    public void get_item_by_name(){
        String username = "BoJackson";
        Item item = new Item();
        List<Item> items = new ArrayList<>();
        items.add(item);

        when(itemRepository.findByName(username)).thenReturn(items);

        ResponseEntity<List<Item>> response = itemController.getItemsByName(username);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        List<Item> returned = response.getBody();
        assertNotNull(returned);
    }

    // attempt lookup with bad name
    @DisplayName("Lookup item with bad username")
    @Test
    public void Lookup_item_with_bad_username(){
        String username = "DarrylStraw";
        Item item = new Item();
        List<Item> items = new ArrayList<>();
        items.add(item);

        when(itemRepository.findByName(username)).thenReturn(null);

        ResponseEntity<List<Item>> response = itemController.getItemsByName(username);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    // attempt look up with bad id
    @DisplayName("Lookup item with bad id")
    @Test
    public void lookup_item_with_bad_id(){
        long id = 1;
        Item item = new Item();

        when(itemRepository.findById(id)).thenReturn(Optional.ofNullable(null));

        ResponseEntity<Item> response = itemController.getItemById(id);
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());

    }


}
