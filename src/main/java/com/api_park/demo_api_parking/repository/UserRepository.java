package com.api_park.demo_api_parking.repository;

import com.api_park.demo_api_parking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String name);

    @Query("select u.role from User u where u.name like :name")
    User.Role findRoleByUserName(String name);
}
