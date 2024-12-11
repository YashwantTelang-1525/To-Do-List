package com.check.notes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.check.notes.model.UserDtls;
import com.check.notes.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;

   
    private final BCryptPasswordEncoder passwordEncoder; // Inject the password encoder
    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = (BCryptPasswordEncoder) passwordEncoder;
    }

    
    @Override
    public UserDtls createUser(UserDtls user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER");  // Default role if none provided
        }
        System.out.println(user);
        user.setPassword(encodedPassword);// Set the encoded password
        
        return userRepository.save(user);    // Save the user with encoded password
    }

    @Override
    public UserDtls getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    

    @Override
    public boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
