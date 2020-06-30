package com.example.BookStore.auth;

import com.example.BookStore.mapper.userMapper;
import com.example.BookStore.model.User;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.BookStore.security.ApplicationRoles.USER_LOGIN;

@Repository("userDao")
public class ApplicationUserDaoService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers() {

        List<ApplicationUser> applicationUsers = new ArrayList<>();
        String sql = "SELECT * FROM user";
        List<User> users = jdbcTemplate.query(sql,new userMapper());
        for(User user : users){
            applicationUsers.add(new ApplicationUser(
                    user.getUsername(),
                    user.getPassword(),
                    USER_LOGIN.getGrantedAuthorities(),
                    true,
                    true,
                    true,
                    true
            ));
        }
        return applicationUsers;
    }
}
