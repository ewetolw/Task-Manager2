package com.pd.eweltol.taskManager2.repository;

import com.pd.eweltol.taskManager2.model.User;
import com.pd.eweltol.taskManager2.model.types.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findAllByRole(Role role);
}