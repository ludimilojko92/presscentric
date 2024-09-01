package com.presscentric.userprovider.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class UserDao {

	@Id
	@UuidGenerator
	private String id;
	private String name;
	private String email;
}
