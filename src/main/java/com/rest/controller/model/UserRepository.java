package com.rest.controller.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    @Query("SELECT u FROM User u WHERE u.userName = ?1")
    List<User> findByUserName(String userName);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.password = ?1, u.title = ?2, u.firstName = ?3, u.lastName = ?4, u.emailAddress = ?5, u.phoneNumber = ?6, u.dob = ?7, u.gender = ?8 WHERE u.id = ?9")
    int updateUserDetails(String password,String title, String firstName,String lastName, String emailAddress, String phoneNumber, String dob, String gender,Long userId);
}