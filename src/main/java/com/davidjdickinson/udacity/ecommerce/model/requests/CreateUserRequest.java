package com.davidjdickinson.udacity.ecommerce.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateUserRequest {

	@NotEmpty(message = "Username must not be null or blank.")
	@JsonProperty
	private String username;

	@NotEmpty(message = "password must not be null or blank.")
	@JsonProperty
	private String password;

	@NotEmpty(message = "confirmPassword must not be null or blank.")
	@JsonProperty
	private String confirmPassword;

	@Override
	public String toString() {
		return "CreateUserRequest{" +
				"username='" + username + '\'' +
				'}';
	}
}
