package com.example.airport;

import com.example.airport.domain.entity.account.User;
import com.example.airport.domain.entity.account.UserRole;
import com.example.airport.persistance.repository.account.UserRepository;
import com.example.airport.persistance.repository.account.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class InitData {

    private final UserRepository userRepository;
    private final UserRoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public InitData(UserRepository userRepository, UserRoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = new BCryptPasswordEncoder(12);
        this.init();
    }

    @Transactional
    public void init(){
        //Roles
        UserRole admin = new UserRole();
        UserRole user = new UserRole();
        UserRole dealer = new UserRole();
        UserRole scheduler = new UserRole();
        admin.setRole("ROLE_ADMIN");
        user.setRole("ROLE_USER");
        dealer.setRole("ROLE_DEALER");
        scheduler.setRole("ROLE_SCHEDULER");
        admin =  roleRepository.save(admin);
        user = roleRepository.save(user);
        dealer = roleRepository.save(dealer);
        scheduler = roleRepository.save(scheduler);
        Set<UserRole> roles1 = new HashSet<>();
        roles1.add(admin);
        roles1.add(dealer);
        roles1.add(user);
        roles1.add(scheduler);
        // USER
        User user1 = new User();
        user1.setName("admin");
        user1.setRoles(roles1);
        user1.setPassword(this.encoder.encode("admin"));

        User user2 = new User();
        user2.setName("dealer");
        user2.setRoles(roles1.stream().filter(t -> t.getRole().equals("ROLE_DEALER") || t.getRole().equals("ROLE_USER")).collect(Collectors.toSet()));
        user2.setPassword(this.encoder.encode("dealer"));
       // user2.setPassword(("dealer"));

        User user3 = new User();
        user3.setName("ja");
        user3.setRoles(roles1.stream().filter(t -> t.getRole().equals("ROLE_USER")).collect(Collectors.toSet()));
        user3.setPassword(this.encoder.encode("123456"));

        User user4 = new User();
        user4.setName("sch");
        user4.setRoles(roles1.stream().filter(t -> t.getRole().equals("ROLE_SCHEDULER")).collect(Collectors.toSet()));
       // user4.setPassword(("sch"));
        user4.setPassword(this.encoder.encode("sch"));

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
    }
}
/*
SELECT u.id, u.name, u.password, ur.role FROM user AS u
JOIN user_to_role AS uto ON (u.id = uto.user_id)
JOIN user_role AS ur ON (uto.user_role_id = ur.id)

 */
