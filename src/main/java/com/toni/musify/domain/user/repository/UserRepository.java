package com.toni.musify.domain.user.repository;

import com.toni.musify.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findByEmailAndPassword(String email,String password);

    Optional<User> findByEmail(String email);
}
