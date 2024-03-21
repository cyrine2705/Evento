package com.app.evento.repositories;

import com.app.evento.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByResetToken(String resetToken);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
