package com.presscentric.userprovider.service;

import com.presscentric.userprovider.dao.UserDao;
import com.presscentric.userprovider.dto.CreateUserInput;
import com.presscentric.userprovider.dto.UpdateUserInput;
import com.presscentric.userprovider.dto.User;
import com.presscentric.userprovider.exception.NotFoundException;
import com.presscentric.userprovider.mapper.UserMapper;
import com.presscentric.userprovider.repository.UserRepository;
import graphql.com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public User getById(final String id) {
		UserDao user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
		return UserMapper.INSTANCE.toDto(user);
	}


	@Transactional(readOnly = true)
	public List<User> getAllUsers() {
		return UserMapper.INSTANCE.toDtos(Lists.newArrayList(userRepository.findAll()));
	}

	@Transactional
	public User createUser(final CreateUserInput userInput) {
		final UserDao entity = UserMapper.INSTANCE.toEntity(userInput);
		return UserMapper.INSTANCE.toDto(userRepository.save(entity));
	}

	@Transactional
	public User updateUser(final String id, final UpdateUserInput userInput) {
		final UserDao user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));

		user.setEmail(userInput.getEmail());
		user.setName(userInput.getName());

		return UserMapper.INSTANCE.toDto(userRepository.save(user));
	}

	@Transactional
	public User deleteUser(final String id) {
		final UserDao userToDelete = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
		userRepository.delete(userToDelete);
		return UserMapper.INSTANCE.toDto(userToDelete);
	}
}
