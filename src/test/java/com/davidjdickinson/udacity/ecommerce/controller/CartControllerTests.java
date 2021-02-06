package com.davidjdickinson.udacity.ecommerce.controller;

import com.davidjdickinson.udacity.ecommerce.controllers.CartController;
import com.davidjdickinson.udacity.ecommerce.exception.ItemNotFoundException;
import com.davidjdickinson.udacity.ecommerce.exception.UsernameNotFoundException;
import com.davidjdickinson.udacity.ecommerce.model.persistence.repositories.CartRepository;
import com.davidjdickinson.udacity.ecommerce.model.persistence.repositories.UserRepository;
import com.davidjdickinson.udacity.ecommerce.TestUtils;
import com.davidjdickinson.udacity.ecommerce.model.persistence.Cart;
import com.davidjdickinson.udacity.ecommerce.model.persistence.Item;
import com.davidjdickinson.udacity.ecommerce.model.persistence.User;
import com.davidjdickinson.udacity.ecommerce.model.persistence.repositories.ItemRepository;
import com.davidjdickinson.udacity.ecommerce.model.requests.ModifyCartRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTests {

    private CartController cartController;

    private UserRepository userRepository = mock(UserRepository.class);

    private CartRepository cartRepository = mock(CartRepository.class);

    private ItemRepository itemRepository = mock(ItemRepository.class);

    @BeforeEach
    public void beforeEach() {
        // inject
        this.cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);
    }

    @DisplayName("Add an item from the cart")
    @ParameterizedTest
    @CsvSource({
            "testuser, 1, 1",
            "testuser, 1, 2",
            "testuser, 1, 10"
    })
    public void add_item_to_cart(
            @AggregateWith(ModifyCartRequestAggregator.class)
                    ModifyCartRequest request){
        Item item = new Item();
        item.setId(1L);
        item.setName("Mountain Goat Biscuits - 30 pack");
        item.setDescription("Yummy buttermilk biscuits made from goat milk from the Andes");
        item.setPrice(new BigDecimal(19.95));

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setItems(new ArrayList<>());
        cart.setTotal(item.getPrice().multiply(new BigDecimal(request.getQuantity())));

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setCart(cart);

        when(userRepository.findByUsername(request.getUsername())).thenReturn(user);
        when(itemRepository.findById(request.getItemId())).thenReturn(Optional.of(item));
        when(cartRepository.save(cart)).thenReturn(null);

        ResponseEntity<Cart> response = cartController.addToCart(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        Cart returnedCart = response.getBody();
        assertNotNull(cart);
        assertEquals(request.getQuantity(), returnedCart.getItems().size());
        assertEquals(returnedCart.getTotal(), cart.getTotal());
    }

    @DisplayName("Remove an item from the cart")
    @ParameterizedTest
    @CsvSource({
            "testuser, 1, 1",
            "testuser, 1, 2",
            "testuser, 1, 10"
    })
    public void remove_item_from_cart(
            @AggregateWith(ModifyCartRequestAggregator.class)
                ModifyCartRequest request) {

        // Precision of 2 digits for all BigDecimal calculations.
        MathContext mc = new MathContext(2);

        // The mock item to remove from the cart
        Item item = new Item();
        item.setId(1L);
        item.setName("Fisherman's Lip Balm");
        item.setDescription("Keeps even the roughest lips soft.");
        item.setPrice(new BigDecimal(new BigInteger("399"), 2));

        // calculate all the totals expected before and after the removal(s)
        BigDecimal total = new BigDecimal(new BigInteger("0"), 2);
        BigDecimal expectedTotalBeforeRemoval = item.getPrice().multiply(new BigDecimal(request.getQuantity()));
        BigDecimal expectedTotalAfterRemoval = new BigDecimal(new BigInteger("0"), 2);

        // Add a group of items into the cart to remove and save the total of all the items.
        List items = new ArrayList<Item>();
        for (int i = 0; i < request.getQuantity(); i++){
            Item itemToAdd = new Item();
            BeanUtils.copyProperties(item, itemToAdd);
            items.add(itemToAdd);
            total = total.add(item.getPrice());
        }
        // check to make sure the items add up to what we expect.
        assertEquals(expectedTotalBeforeRemoval, total);

        Cart cart = new Cart();
        cart.setId(1L);
        cart.setItems(items);
        cart.setTotal(total);

        User user = new User();
        user.setId(1);
        user.setUsername("testuser");
        user.setPassword("password");
        user.setCart(cart);
        cart.setUser(user);

        when(userRepository.findByUsername(request.getUsername())).thenReturn(user);
        when(itemRepository.findById(request.getItemId())).thenReturn(Optional.of(item));
        when(cartRepository.save(cart)).thenReturn(null);

        ResponseEntity<Cart> response = cartController.removeFromCart(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        Cart returnedCart = response.getBody();
        assertNotNull(cart);

        // all the items should have been removed.
        assertEquals(0, returnedCart.getItems().size());
        assertEquals(expectedTotalAfterRemoval, returnedCart.getTotal());
    }

    @Test
    @DisplayName("Add to cart with bad user name")
    public void add_item_to_cart_with_bad_username(){
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("test");
        when(userRepository.findByUsername(request.getUsername())).thenReturn(null);
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {cartController.addToCart(request);});
    }

    @Test
    @DisplayName("Remove from cart with bad user name")
    public void remove_item_from_cart_with_bad_username(){
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("test");
        when(userRepository.findByUsername(request.getUsername())).thenReturn(null);
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {cartController.removeFromCart(request);});
    }

    @Test
    @DisplayName("Add to cart with bad item id")
    public void add_item_to_cart_with_bad_item_id(){
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("test");
        request.setItemId(1L);
        when(userRepository.findByUsername(request.getUsername())).thenReturn(new User());
        when(itemRepository.findById(request.getItemId())).thenReturn(Optional.ofNullable(null));

        Assertions.assertThrows(ItemNotFoundException.class, () -> {cartController.addToCart(request);});
    }

    @Test
    @DisplayName("Remove from cart with bad item id")
    public void remove_item_from_cart_with_bad_item_id(){
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("test");
        request.setItemId(1L);
        when(userRepository.findByUsername(request.getUsername())).thenReturn(new User());
        when(itemRepository.findById(request.getItemId())).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(ItemNotFoundException.class, () -> {cartController.removeFromCart(request);});
    }

    @Test
    @DisplayName("Attempt to remove too many items from cart")
    public void remove_too_many_items_from_cart(){
        ModifyCartRequest request = new ModifyCartRequest();
        request.setItemId(1L);
        request.setUsername("test");
        request.setQuantity(1);

        // Precision of 2 digits for all BigDecimal calculations.
        MathContext mc = new MathContext(2);

        // The mock item to remove from the cart
        Item item = new Item();
        item.setId(1L);
        item.setName("Fisherman's Lip Balm");
        item.setDescription("Keeps even the roughest lips soft.");
        item.setPrice(new BigDecimal(new BigInteger("399"), 2));

        // The cart total should be 0 and stay 0 after the call.
        BigDecimal startTotal = new BigDecimal(new BigInteger("0"), 2);
        BigDecimal expectedTotal = new BigDecimal(new BigInteger("0"), 2);

        // The mock cart which is empty.
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setItems(new ArrayList<Item>());
        cart.setTotal(startTotal);

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setCart(cart);

        when(userRepository.findByUsername(request.getUsername())).thenReturn(user);
        when(itemRepository.findById(request.getItemId())).thenReturn(Optional.of(item));
        when(cartRepository.save(cart)).thenReturn(null);

        ResponseEntity<Cart> response = cartController.removeFromCart(request);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        Cart returnedCart = response.getBody();
        assertNotNull(cart);

        // cart should be empty and the total price should be 0.00.
        assertEquals(0, returnedCart.getItems().size());
        assertEquals(expectedTotal, returnedCart.getTotal());
    }

}

