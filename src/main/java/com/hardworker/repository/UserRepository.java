package com.hardworker.repository;

import com.hardworker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 *
 * @author kain
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID>{
    User findByLogin(String login);
}
