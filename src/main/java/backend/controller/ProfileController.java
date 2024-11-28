package backend.controller;

import backend.dto.ProfileDTO;
import backend.entity.Profile;
import backend.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;

@RestController("/profile")
public class ProfileController {
    private Profile profile;
    @Autowired
    private ProfileService profileService;

    @GetMapping("/find/{userId}")
    public Profile findProfileByUserId(@PathVariable Long userId ) {
        return profileService.findProfileByUserId(userId);
    }


    @PostMapping("/create")
    public Profile createProfile(@RequestParam ProfileDTO profileDTO) {
        profile.setUserId(profileDTO.getUserId());
        profile.setNickName(profileDTO.getNickname());
        profile.setRealName(profileDTO.getRealName());
        profile.setBio(profileDTO.getBio());
        profile.setLocation(profileDTO.getLocation());
        profile.setAvatar(profileDTO.getAvatar());
        return profileService.createProfile(profileDTO);
    }

    @PostMapping("/update_avatar")
    public Profile updateAvatar(@RequestParam ProfileDTO profileDTO, @RequestParam Long profileId) {
        profile.setAvatar(profileDTO.getAvatar());
        return profileService.updateAvatar(profileDTO, profileId);
    }


}
