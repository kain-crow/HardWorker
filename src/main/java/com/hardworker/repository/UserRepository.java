package com.hardworker.repository;

import com.hardworker.entity.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kain
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID>{
    
    @Query("select o from sec$User o where o.login = ?1")
    User getUserByLogin(String login);
}
