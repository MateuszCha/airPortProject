package com.example.airport.persistance.repository.account;

import com.example.airport.domain.entity.account.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Integer> {
    UserRole findUserRoleByRole(String role);
}
