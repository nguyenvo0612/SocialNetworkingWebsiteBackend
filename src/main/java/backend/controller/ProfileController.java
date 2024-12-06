package backend.controller;

import backend.dto.ProfileDTO;
import backend.entity.Profile;
import backend.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("api/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @GetMapping("/find/{id}")
    public Profile findProfileById(@PathVariable Long id) {
        return  profileService.findProfileByAccountId(id);
    }


    @PostMapping("/create")
    public Profile createProfile(@RequestBody ProfileDTO profileDTO) {
        return profileService.createProfileWithAvatar(profileDTO);
    }

    @PostMapping("/update_avatar")
    public Profile updateAvatar(@RequestBody ProfileDTO profileDTO, @PathVariable Long profileId) {

        return profileService.updateAvatar(profileDTO, profileId);
    }

    @PutMapping("/update/{profileId}")
    public Profile updateProfile(@PathVariable Long profileId, @RequestBody ProfileDTO profileDTO) {
        return profileService.updateProfile(profileId, profileDTO);
    }

    @DeleteMapping("/delete/{profileId}")
    public void deleteProfile(@PathVariable Long profileId) {
        profileService.deleteProfile(profileId);
    }

}
