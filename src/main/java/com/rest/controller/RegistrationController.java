package com.rest.controller;

import com.rest.controller.model.ResourceNotFoundException;
import com.rest.controller.model.User;
import com.rest.controller.model.UserLinks;
import com.rest.controller.model.UserRepository;
import com.rest.controller.model.UserLinksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("users/")
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLinksRepository userLinksRepository;

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

    @GetMapping("/getbyusername/{userName}")
    public User getUserByUserName(@PathVariable(value = "userName") String userName)
            throws ResourceNotFoundException {
        System.out.println("Attempting get for UserName" + userName);
        try {
            Optional<User> user = userRepository.findUserByUserName(userName);
            if (user.isPresent()) {
                System.out.print("user is "+ user.get().getId());
                return user.get();
            } else {
                System.out.print("user is not found");
                return null;
            }
        }
        catch(Exception ex) {
            throw new ResourceNotFoundException("User not found for this userName :: " + userName);
        }
    }

    @GetMapping("/getlinksbyusername/{userName}")
    public List<UserLinks> getUserLinksByUserName(@PathVariable(value = "userName") String userName)
            throws ResourceNotFoundException {
        System.out.println("Attempting get for links for UserName" + userName);
        try {
            List<UserLinks> userLinksFound = userLinksRepository.findUserLinksByAll("Guest");
            System.out.println("userLinksFound" + userLinksFound);
            if (!userLinksFound.isEmpty()) {
                return userLinksFound;
            } else {
                System.out.print("user links are not found");
                return null;
            }
        }
        catch(Exception ex) {
            throw new ResourceNotFoundException("User Links not found for this userName :: " + userName);
        }
    }


    @PostMapping("/register")
    public User createUser(@Valid @RequestBody User user) {
        System.out.println("saving user details");
        System.out.println("The user is:" + user);
        User userSave = userRepository.save(user);
        Optional<User> savedUser = userRepository.findById(userSave.getId());
        System.out.println("saved user" + savedUser);
        return userSave;
    }


    @PostMapping("/saveuserlinks")
    public UserLinks getUserLinkByName(@Valid @RequestBody UserLinks userLinks) {
        System.out.println("saving user link details");

        System.out.println("userLinks.getId():" + userLinks.getId());
        System.out.println("userLinks.getUserName():" + userLinks.getUserName());
        System.out.println("userLinks.getTitle()" + userLinks.getTitle());
        System.out.println("userLinks.getUrl()" + userLinks.getUrl());
        UserLinks userLinksSave = userLinksRepository.save(userLinks);
        System.out.println("after save get id" + userLinksSave.getId());
        System.out.println("after save get username" + userLinksSave.getUserName());


        //Optional<UserLinks> savedUserLinks = userLinksRepository.findUserLinksByAll(userLinksSave.getUserName());
        //System.out.println("saved user links" + savedUserLinks);
        return userLinksSave;
    }
}

