package com.cos.photogramstart.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

//    User findByUsername(String username);
    public Optional<User> findByUsername(String username);
}
