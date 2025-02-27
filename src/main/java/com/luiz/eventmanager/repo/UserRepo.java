package com.luiz.eventmanager.repo;

import com.luiz.eventmanager.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Integer> {
    public User findByEmail(String email);
}
