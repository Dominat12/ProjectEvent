package com.example.eventpoc.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<AppUser> all() {
        return userService.getAll();
    }

    @GetMapping("/user/{id}")
    public AppUser single(@PathVariable("id") long userId) {
        Optional<AppUser> user = userService.getUserById(userId);
        return user.orElseThrow(() -> new RuntimeException("No user found for this id"));
    }

    @PostMapping("/user")
    public Long create(@RequestBody AppUser user) {
        return userService.create(user).getId();
    }

    @PutMapping("/user")
    public Long update(@RequestBody AppUser user) {
        return userService.update(user).getId();
    }
    
    /* ***************
     Test Endpoints
     **************** */

    /**
     * Erstellt einen Beispiel Nutzer mit Namen "SampleUser"
     * und gibt die id des Nutzers zurück
     *
     * @return
     */
    @PostMapping("/sampleuser")
    public Long create() {
        return userService.create(new AppUser("SampleUser")).getId();
    }
}
