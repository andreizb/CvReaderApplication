package com.cv.service;

import com.cv.exception.InstanceAlreadyExistsException;
import com.cv.exception.NotFoundException;
import com.cv.model.User;
import com.cv.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private long idSequence;

    @PostConstruct
    public void init() {
        idSequence = userRepository.getIDSequence();
    }

    public User getByUsername(String username) throws Exception {
        return userRepository.getByUsername(username)
                .orElseThrow(NotFoundException::new);
    }

    public void addByUsername(String username) throws Exception {
        if (userRepository.existsByUsername(username)) {
            throw new InstanceAlreadyExistsException();
        }
        userRepository.addUser(new User(username, idSequence++));
    }
}
