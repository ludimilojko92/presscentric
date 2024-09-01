package com.presscentric.userprovider.dto;

import com.presscentric.userprovider.validation.EmailField;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@NotEmpty(message = "User's id cannot be null or empty")
	private String id;

	private String name;

	@NotEmpty(message = "User's email cannot be null or empty")
	@EmailField
	private String email;
}
