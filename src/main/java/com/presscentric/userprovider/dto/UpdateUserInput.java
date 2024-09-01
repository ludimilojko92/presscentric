package com.presscentric.userprovider.dto;

import com.presscentric.userprovider.validation.EmailField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserInput {

	private String name;

	@EmailField
	private String email;
}
