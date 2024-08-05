package com.rasimalimgulov.userservice.user_service.dao;
import com.rasimalimgulov.userservice.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDAO extends JpaRepository<User, Long> {
}
