package com.presscentric.userprovider.dgs;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration;
import com.presscentric.userprovider.dto.CreateUserInput;
import com.presscentric.userprovider.dto.UpdateUserInput;
import com.presscentric.userprovider.dto.User;
import com.presscentric.userprovider.service.UserService;
import graphql.ErrorClassification;
import graphql.ExecutionResult;
import graphql.GraphQLError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static graphql.Assert.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = { DgsAutoConfiguration.class, UserFetcher.class})
class UserFetcherTest {

	private static final List<User> USERS = List.of(User.builder().id("123").email("john@gmail.com").name("John Malkovich").build(),
			User.builder().id("321").email("djokovic@mail.com").name("Novak").build());

	@Autowired
	private DgsQueryExecutor dgsQueryExecutor;

	@MockBean
	private UserService userService;

	@Test
	void userTest() {
		when(userService.getById("123")).thenReturn(USERS.get(0));

		String query = "{ user(id: \"123\") { id name email } }";
		ExecutionResult execute = dgsQueryExecutor.execute(query);
		Map<String, Map<String, String>> data = execute.getData();

		Map<String, String> user = data.get("user");

		assertEquals("John Malkovich", user.get("name"));
		assertEquals("john@gmail.com", user.get("email"));
	}

	@Test
	void usersTest() {
		when(userService.getAllUsers()).thenReturn(USERS);
		String query = "{ users { id name email } }";
		ExecutionResult execute = dgsQueryExecutor.execute(query);
		Map<String, List<Map<String, String>>> data = execute.getData();
		List<Map<String, String>> users = data.get("users");

		assertEquals(2, users.size());

		assertEquals("John Malkovich", users.get(0).get("name"));
		assertEquals("john@gmail.com", users.get(0).get("email"));

		assertEquals("Novak", users.get(1).get("name"));
		assertEquals("djokovic@mail.com", users.get(1).get("email"));
	}

	@Test
	void createUserTest() {
		when(userService.createUser(any(CreateUserInput.class))).thenReturn(USERS.get(1));

		String query = "mutation { createUser(input: { name:\"Novak\", email: \"djokovic@mail.com\"}) { id name email } }";
		ExecutionResult execute = dgsQueryExecutor.execute(query);
		Map<String, Map<String, String>> data = execute.getData();

		Map<String, String> createdUser = data.get("createUser");

		assertEquals("Novak", createdUser.get("name"));
		assertEquals("djokovic@mail.com", createdUser.get("email"));
	}

	@Test
	void updateUserTest() {
		when(userService.updateUser(anyString(), any(UpdateUserInput.class))).thenReturn(USERS.get(1));

		String query = "mutation { updateUser(id: \":123\", input: { name:\"Novak\", email: \"djokovic@mail.com\"}) { id name email } }";
		ExecutionResult execute = dgsQueryExecutor.execute(query);
		Map<String, Map<String, String>> data = execute.getData();

		Map<String, String> updatedUser = data.get("updateUser");

		assertEquals("Novak", updatedUser.get("name"));
		assertEquals("djokovic@mail.com", updatedUser.get("email"));
	}

	@Test
	void deleteUserTest() {
		when(userService.deleteUser(anyString())).thenReturn(USERS.get(1));

		String query = "mutation { deleteUser(id: \":123\") { id name email } }";
		ExecutionResult execute = dgsQueryExecutor.execute(query);
		Map<String, Map<String, String>> data = execute.getData();

		Map<String, String> updatedUser = data.get("deleteUser");

		assertEquals("Novak", updatedUser.get("name"));
		assertEquals("djokovic@mail.com", updatedUser.get("email"));
	}

	@Test
	void validationExceptionTest() {
		String query = "{ user(id: null) { id name email } }";
		ExecutionResult execute = dgsQueryExecutor.execute(query);

		List<GraphQLError> errors = execute.getErrors();

		assertNotNull(errors);
		assertFalse(errors.isEmpty());
	}
}
