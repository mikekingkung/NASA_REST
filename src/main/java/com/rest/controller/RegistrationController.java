package com.rest.controller;

import com.rest.controller.model.ResourceNotFoundException;
import com.rest.controller.model.User;
import com.rest.controller.model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("users/")
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/getuser/{id}")
    public User getUserById(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {
        System.out.println("Attempting get for id" + userId);
        Optional<User> user = Optional.empty();
        try {
            user = userRepository.findById(userId);

            if (user.isPresent()) {
                System.out.print("user is "+ user.get().getId());
                return user.get();
            } else {
                System.out.print("user is not found");
                return null;

            }
        }
        catch(Exception ex) {
            throw new ResourceNotFoundException("User not found for this id :: " + userId);
        }

    }

    @PostMapping("/register")
    public User createUser(@Valid @RequestBody User user) {
        System.out.println("saving user details");
        User userSave = userRepository.save(user);
        Optional<User> savedUser = userRepository.findById(userSave.getId());
        System.out.println("saved user" + savedUser);
        return userSave;
    }
}
