package com.davidjdickinson.udacity.ecommerce.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {

	@JsonProperty
	private String username;

	@JsonProperty
	private String password;

	@JsonProperty
	private String confirmPassword;

	@Override
	public String toString() {
		return "CreateUserRequest{" +
				"username='" + username + '\'' +
				'}';
	}
}