package com.lin.missyou.repository;

import com.lin.missyou.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    Optional<User> findByOpenid(String openid);
    User findFirstById(Long id);
    User findByUnifyUid(Long id);
}
