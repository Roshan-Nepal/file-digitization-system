package com.roshan.filedigitizationsystem.repo;

import com.roshan.filedigitizationsystem.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserInfo, Long> {
    @Query("SELECT u from UserInfo u where u.username = :username")
    UserInfo findByUsername(String username);
}
