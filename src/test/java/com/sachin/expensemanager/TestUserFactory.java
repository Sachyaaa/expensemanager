package com.sachin.expensemanager;

import com.sachin.expensemanager.model.User;
import com.sachin.expensemanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class TestUserFactory {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(String email, String password) {
        return createUserWithRole(email, password, "USER");
    }

    public User createAdmin(String email, String password) {
        return createUserWithRole(email, password, "ADMIN");
    }

    private User createUserWithRole(String email, String password, String role) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        return userRepository.save(user);
    }
}
