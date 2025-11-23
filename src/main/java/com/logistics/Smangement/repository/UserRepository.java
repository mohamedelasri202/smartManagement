package com.logistics.Smangement.repository;

import org.springframework.data.mongodb.repository.MongoRepository;



import com.logistics.Smangement.entity.User;
import com.logistics.Smangement.enums.Speciality;
import com.logistics.Smangement.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByLogin(String login);


    Page<User> findByRole(UserRole role, Pageable pageable);


    Page<User> findByRoleAndSpeciality(UserRole role, Speciality speciality, Pageable pageable);


    boolean existsByLogin(String login);
}