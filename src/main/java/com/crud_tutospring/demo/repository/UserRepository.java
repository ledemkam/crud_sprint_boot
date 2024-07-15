package com.crud_tutospring.demo.repository;

import com.crud_tutospring.demo.modelEntity.UserInfos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserInfos, Long> {
    UserInfos findByUsername(String username);

}
