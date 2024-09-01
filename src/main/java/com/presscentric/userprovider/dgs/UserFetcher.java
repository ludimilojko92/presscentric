package com.presscentric.userprovider.dgs;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.presscentric.userprovider.dto.CreateUserInput;
import com.presscentric.userprovider.dto.UpdateUserInput;
import com.presscentric.userprovider.dto.User;
import com.presscentric.userprovider.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@DgsComponent
@RequiredArgsConstructor
@Validated
public class UserFetcher {

	private final UserService userService;

	@DgsQuery
	public User user(@InputArgument @NotEmpty(message = "ID cannot be null or empty") final String id) {
		return userService.getById(id);
	}

	@DgsQuery
	public List<User> users() {
		return userService.getAllUsers();
	}

	@DgsMutation
	public User createUser(@InputArgument("input") @NotNull @Valid final CreateUserInput input) {
		return userService.createUser(input);
	}

	@DgsMutation
	public User updateUser(@InputArgument @NotEmpty String id, @InputArgument("input") @NotNull @Valid final UpdateUserInput input) {
		return userService.updateUser(id, input);
	}

	@DgsMutation
	public User deleteUser(@InputArgument @NotEmpty final String id) {
		return userService.deleteUser(id);
	}
}
