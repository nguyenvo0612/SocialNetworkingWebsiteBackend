package backend.service;

import backend.dto.ProfileDTO;
import backend.entity.Profile;
import backend.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public boolean findProfileByUserId(Long userId) {

        if (profileRepository.findByUserId(userId) == null) {
            return false;
        }return true;
    }



    public Profile createProfile(ProfileDTO profileDTO) {
        Profile profile = new Profile();
        profile.setUserId(profileDTO.getUserId());
        profile.setRealName(profileDTO.getRealName());
        profile.setNickname(profileDTO.getNickname());
        profile.setBio(profileDTO.getBio());
        profile.setAvatar(profileDTO.getAvatar());
        return profileRepository.save(profile);
    }


    public Profile updateAvatar(ProfileDTO profileDTO,Long profileId) {
        Profile profile = profileRepository.getById(profileId);
        profile.setAvatar(profileDTO.getAvatar());
        return profileRepository.save(profile);
    }
}
//        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
//                "cloud_name", "dzq2dpudl",
//                "api_key", "346147426461483",
//                "api_secret", "ZTnk5B3o4Jy4fAqZxum3ap2i62c",
//                "secure", true));