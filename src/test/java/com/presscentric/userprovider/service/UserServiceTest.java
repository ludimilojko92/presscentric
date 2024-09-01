package com.presscentric.userprovider.service;

import com.presscentric.userprovider.dao.UserDao;
import com.presscentric.userprovider.dto.CreateUserInput;
import com.presscentric.userprovider.dto.UpdateUserInput;
import com.presscentric.userprovider.dto.User;
import com.presscentric.userprovider.exception.NotFoundException;
import com.presscentric.userprovider.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static graphql.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest
{

	private static final UserDao JOHN_USER = UserDao.builder().id("123").email("user@mail.com").name("John").build();
	@Mock
	private UserRepository repository;

	@InjectMocks
	private UserService userService;

	@Test
	void findUserByIdTest() {
		when(repository.findById("abc")).thenReturn(Optional.of(JOHN_USER));

		User user = userService.getById("abc");

		verify(repository, times(1)).findById("abc");
		assertNotNull(user);
		assertEquals("John", user.getName());
	}

	@Test
	void createUserTest() {
		when(repository.save(any(UserDao.class))).thenReturn(JOHN_USER);

		CreateUserInput john = CreateUserInput.builder().name("John").email("mail@mail.com").build();

		User user = userService.createUser(john);

		verify(repository, times(1)).save(any(UserDao.class));
		assertNotNull(user);
		assertEquals("John", user.getName());
	}

	@Test
	void updateUserTest() {
		when(repository.findById("123")).thenReturn(Optional.of(JOHN_USER));
		when(repository.save(any(UserDao.class))).thenReturn(JOHN_USER);

		UpdateUserInput john = UpdateUserInput.builder().name("John").email("mail@mail.com").build();

		User user = userService.updateUser("123", john);

		verify(repository, times(1)).findById("123");
		verify(repository, times(1)).save(any(UserDao.class));
		assertNotNull(user);
		assertEquals("123", user.getId());
		assertEquals("John", user.getName());
	}

	@Test
	void getAllUsersTest() {
		when(repository.findAll()).thenReturn(List.of(JOHN_USER));

		List<User> users = userService.getAllUsers();

		verify(repository, times(1)).findAll();
		assertNotNull(users);
		assertEquals(1, users.size());
		assertEquals("John", users.get(0).getName());
	}

	@Test
	void deleteUserTest() {
		when(repository.findById("123")).thenReturn(Optional.of(JOHN_USER));

		userService.deleteUser("123");

		verify(repository, times(1)).findById("123");
		verify(repository, times(1)).delete(JOHN_USER);
	}

	@Test
	void userNotFoundExceptionTest() {
		NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.getById("abc"));
		assertEquals("User not found", exception.getMessage());
	}
}
