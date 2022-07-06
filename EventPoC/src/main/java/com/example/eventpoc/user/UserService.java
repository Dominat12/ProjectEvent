package com.example.eventpoc.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    /* Single User CRUD Operations */
    public AppUser create(AppUser user){
        return userRepository.save(user);
    }

    public Optional<AppUser> getUserById(Long id){
        return userRepository.findById(id);
    }

    public Optional<AppUser> getUserByObj(AppUser user){
        if(user == null){
            return Optional.empty();
        }
        return userRepository.findById(user.getId());
    }

    public AppUser update(AppUser user){
        return userRepository.save(user);
    }

    public void delete(AppUser user){
        userRepository.delete(user);
    }

    public void delete(long id){userRepository.deleteById(id);}

    /* Multiple User CRUD Operations */
    public List<AppUser> create(Iterable<AppUser> users){
        return userRepository.saveAll(users);
    }

    public List<AppUser> getAllUsers(){
        return userRepository.findAll();
    }

    public List<AppUser> getUserById(Iterable<Long> ids){
        return userRepository.findAllById(ids);
    }

    public List<AppUser> getUserByObj(Collection<AppUser> users){
        List<Long> userIds = users.stream().map(u -> u.getId()).collect(Collectors.toList());
        return userRepository.findAllById(userIds);
    }

    public List<AppUser> getUserByObj(AppUser... users ){
        List<Long> userIds = Arrays.stream(users).map(u -> u.getId()).collect(Collectors.toList());
        return userRepository.findAllById(userIds);
    }

    public List<AppUser> update(Iterable<AppUser> users){
        return userRepository.saveAll(users);
    }

    public void delete(Iterable<AppUser> users){
        userRepository.deleteAllInBatch(users);
    }

    /**
     * Das ist nur für Testzwecke oder TODO muss später durch Testcontainer ersetzte werden!
     */
    public void deleteAll(){
        userRepository.deleteAll();
    }
}
