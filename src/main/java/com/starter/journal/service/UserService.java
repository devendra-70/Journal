package com.starter.journal.service;

import com.starter.journal.entity.User;
import com.starter.journal.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user){
        userRepository.save(user);
    }

    public Optional<User> findUserById(ObjectId id){
        return userRepository.findById(id);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void deleteUserById(ObjectId id){
        userRepository.deleteById(id);
    }

    public User findByUserName(String username){
        return userRepository.findByUserName(username);
    }

    public User updateUser(User updateduser,String userName){
        User userInDb = findByUserName(userName);
        if(userInDb!=null){
            userInDb.setUserName(updateduser.getUserName());
            userInDb.setPassword(updateduser.getPassword());
        }
        saveUser(userInDb);
        return userInDb;
    }




}
