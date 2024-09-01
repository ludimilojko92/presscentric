package com.presscentric.userprovider.mapper;

import com.presscentric.userprovider.dao.UserDao;
import com.presscentric.userprovider.dto.CreateUserInput;
import com.presscentric.userprovider.dto.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	User toDto(UserDao userDao);
	UserDao toEntity(User user);
	UserDao toEntity(CreateUserInput createUser);
	List<User> toDtos(List<UserDao> entities);
}
