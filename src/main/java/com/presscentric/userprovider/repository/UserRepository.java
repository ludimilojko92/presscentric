package com.presscentric.userprovider.repository;

import com.presscentric.userprovider.dao.UserDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserDao, String> {
}
