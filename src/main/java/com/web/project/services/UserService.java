package com.web.project.services;


import com.web.project.entities.User;
import com.web.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Component
@Repository
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Transactional
    public User save(User user) {
        user.setStatus("Low Risk");
        return userRepository.save(user);
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    @Transactional
    public void delete(String id) {
        userRepository.deleteById(id);
    }



}
