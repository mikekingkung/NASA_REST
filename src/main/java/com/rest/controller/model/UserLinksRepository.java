package com.rest.controller.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLinksRepository extends JpaRepository<UserLinks,Long>{


    @Query("SELECT u FROM UserLinks u WHERE u.userName = ?1")
    List<UserLinks> findUserLinksByAll(String userName);

}