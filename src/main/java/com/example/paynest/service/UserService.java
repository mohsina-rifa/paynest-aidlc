package com.example.paynest.service;

import com.example.paynest.model.User;
import com.example.paynest.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void create(User user) {
        userRepository.save(user);
    }

    public User updateById(Integer id, User user) {
        User existingUser = findById(id);
        existingUser.setName(user.getName());
        existingUser.setPassword(user.getPassword());

        return userRepository.save(existingUser);
    }

    public void deleteById(Integer id) {
        User existingUser = findById(id);
        userRepository.delete(existingUser);
//         userRepository.deleteById(id);
    }

}
