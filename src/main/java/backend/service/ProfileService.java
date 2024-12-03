package backend.service;

import backend.dto.ProfileDTO;
import backend.entity.Profile;
import backend.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public Profile findProfileByUserId(Long userId) {
        return profileRepository.findProfileByUserId(userId);
    }

    public Profile createProfile(ProfileDTO profileDTO) {
        Profile profile = new Profile();
        profile.setUserId(profileDTO.getUserId());
        profile.setRealName(profileDTO.getRealName());
        profile.setNickName(profileDTO.getNickname());
        profile.setBio(profileDTO.getBio());
        profile.setAvatar(profileDTO.getAvatar());
        return profileRepository.save(profile);
    }

    public Profile updateProfile(Long profileId, ProfileDTO profileDTO) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new RuntimeException("Profile not found"));
        profile.setNickName(profileDTO.getNickname());
        profile.setRealName(profileDTO.getRealName());
        profile.setBio(profileDTO.getBio());
        profile.setLocation(profileDTO.getLocation());
        profile.setAvatar(profileDTO.getAvatar());
        return profileRepository.save(profile);
    }

    public void deleteProfile(Long profileId) {
        profileRepository.deleteById(profileId);
    }

    public Profile updateAvatar(ProfileDTO profileDTO, Long profileId) {
        Profile profile = profileRepository.findById(profileId)
            .orElseThrow(() -> new RuntimeException("Profile not found"));
        profile.setAvatar(profileDTO.getAvatar());
        return profileRepository.save(profile);
    }
}