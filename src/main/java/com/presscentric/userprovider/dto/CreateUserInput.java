package com.presscentric.userprovider.dto;

import com.presscentric.userprovider.validation.EmailField;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserInput {

	private String name;

	@NotEmpty(message = "User's email cannot be null or empty")
	@EmailField
	private String email;
}
