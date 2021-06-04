package com.example.airport.persistance.service.account;

import com.example.airport.domain.entity.account.User;
import com.example.airport.domain.entity.account.UserRole;
import com.example.airport.persistance.exception.NoFoundEntity;
import com.example.airport.persistance.repository.account.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Component
public class MyUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public MyUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        if(Objects.isNull(s) || s.isEmpty()){
            throw new IllegalArgumentException("login of user" + s + ";");
        }
        User user = userRepository.findUserByName(s);
        if(Objects.isNull(user)){
            throw new NoFoundEntity("user by name:" + s);
        }
        return createUserDetail(user);
    }

    private UserDetails createUserDetail(User user){
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getName())
                .password(user.getPassword())
                .authorities(getAuthority(user.getRoles()))
                .build();
        return userDetails;
    }
    private Collection<? extends GrantedAuthority> getAuthority(Collection<UserRole> roles){
        Set<GrantedAuthority> setOfRole = new HashSet<>();
        for(UserRole element : roles){
            setOfRole.add(new SimpleGrantedAuthority(element.getRole()));
        }
        return setOfRole;
    }

}
