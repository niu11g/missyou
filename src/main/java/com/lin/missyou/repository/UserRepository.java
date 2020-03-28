package com.lin.missyou.repository;

import com.lin.missyou.model.Theme;
import com.lin.missyou.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    Optional<User> findByOpenId(String openid);
    User findFirstById(Long id);
    User findByUnifyUid(Long id);
}
