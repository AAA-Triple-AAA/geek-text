package com.springbreakers.geektext.controller;

import com.springbreakers.geektext.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import com.springbreakers.geektext.model.Profile;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {this.profileService = profileService;}

    @GetMapping
    public List<Profile> getProfiles() {
        return profileService.getProfiles();
    }

    /*
    To-Do:
    - profile stuff
     */
}
