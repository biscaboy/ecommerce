package com.davidjdickinson.udacity.ecommerce.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class ModifyCartRequest {

	@NotEmpty(message = "Username must not be null or blank.")
	@JsonProperty
	private String username;

	@NotEmpty(message = "itemId must not be null or blank.")
	@Positive(message = "itemId must be greater than 0")
	@JsonProperty
	private long itemId;

	@NotEmpty(message = "quantity must not be null or blank.")
	@Positive(message = "quantity must be greater than 0")
	@JsonProperty
	private int quantity;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	

}
