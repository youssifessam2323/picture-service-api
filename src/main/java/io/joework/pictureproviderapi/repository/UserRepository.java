package io.joework.pictureproviderapi.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import io.joework.pictureproviderapi.domain.User;

public interface UserRepository extends CrudRepository<User,Long>{
    Optional<User> findByUsername(String username);
}
