package com.example.eventpoc.user;


import com.example.eventpoc.common.SpecificException;
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
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /* Single User CRUD Operations */

    /**
     * Erstellt den übergebenen Nutzer in der Datenbank.
     * Nur Nutzer ohne Id sind erlaubt.
     * Für das Updaten eines Nutzers die update-Funktion verwenden.
     *
     * @param user
     * @return
     */
    public AppUser create(AppUser user) {
        /* Nur Nutzer ohne Id werden "neu" erstellt */
        if (user.getId() != 0) {
            throw new SpecificException("Keine Id bei einer User-Erstellung!");
        }
        return userRepository.save(user);
    }

    public Optional<AppUser> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<AppUser> getUserByObj(AppUser user) {
        if (user == null) {
            return Optional.empty();
        }
        return userRepository.findById(user.getId());
    }

    public AppUser update(AppUser user) {
        return userRepository.save(user);
    }

    public void delete(AppUser user) {
        userRepository.delete(user);
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }

    /* Multiple User CRUD Operations */

    /**
     * Erstellt den übergebenen Nutzer in der Datenbank.
     * Nur Nutzer ohne Id sind erlaubt.
     * Alle Nutzer ohne Id werden immer persistiert.
     * Für das Updaten eines Nutzers die update-Funktion verwenden.
     *
     * @param users
     * @return
     */
    public List<AppUser> create(Collection<AppUser> users) {
        List<AppUser> savedUsers = users.stream().toList();
        /* Nur Nutzer ohne Id werden "neu" erstellt */
        users.forEach(u -> {
            if (u.getId() != 0) {
                throw new SpecificException("Keine Id bei einer User-Erstellung!");
            }
            savedUsers.remove(u);
        });

        return userRepository.saveAll(savedUsers);
    }

    public List<AppUser> getAll() {
        return userRepository.findAll();
    }

    public List<AppUser> getUserById(Iterable<Long> ids) {
        return userRepository.findAllById(ids);
    }

    public List<AppUser> getUserByObj(Collection<AppUser> users) {
        List<Long> userIds = users.stream().map(u -> u.getId()).collect(Collectors.toList());
        return userRepository.findAllById(userIds);
    }

    public List<AppUser> getUserByObj(AppUser... users) {
        List<Long> userIds = Arrays.stream(users).map(u -> u.getId()).collect(Collectors.toList());
        return userRepository.findAllById(userIds);
    }

    public List<AppUser> update(Iterable<AppUser> users) {
        return userRepository.saveAll(users);
    }

    public void delete(Iterable<AppUser> users) {
        userRepository.deleteAllInBatch(users);
    }

    /**
     * Das ist nur für Testzwecke oder TODO muss später durch Testcontainer ersetzte werden!
     */
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
