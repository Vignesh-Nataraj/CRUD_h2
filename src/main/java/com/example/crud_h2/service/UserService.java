package com.example.crud_h2.service;

import com.example.crud_h2.model.User;
import com.example.crud_h2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    public List<User> getAllUsers() {
       return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public void deleteUserById(Long id){
         userRepository.deleteById(id);
    }

    public User updateUser(Long id,User user){
        User updateUser = userRepository.findById(id).orElseThrow();
        updateUser.setName(user.getName());
        updateUser.setEmail(user.getEmail());
        updateUser.setDob(user.getDob());
        updateUser.setNickname(user.getNickname());
        return userRepository.save(updateUser);

    }

    public User patchUser(Long id,User user){
        User updateUser = userRepository.findById(id).orElseThrow();
        if(user.getName()!=null){
            updateUser.setName(user.getName());
        }
        if(user.getEmail()!=null){
            updateUser.setEmail(user.getEmail());
        }
        if(user.getDob()!=null){
            updateUser.setDob(user.getDob());
        }
        if(user.getNickname()!=null){
            updateUser.setNickname(user.getNickname());
        }
        return userRepository.save(updateUser);
    }

    public List<User> getFilteredUsers(){
        List<User> users=userRepository.findAll();
        List<User> filteredUsers=users.stream().filter(user -> user.getId().MAX_VALUE>2).toList();
        return filteredUsers;
    }

}
