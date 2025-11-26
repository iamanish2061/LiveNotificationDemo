package com.LiveNotificationDemo.repo;

import com.LiveNotificationDemo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<Users> findByRefreshToken(String token);

    List<Users> findByRole(Users.Role roleAdmin);
}
