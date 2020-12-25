package com.opisir.repository;

import com.opisir.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Auther: dingjn
 * @Desc:
 */
public interface UserRepo extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
