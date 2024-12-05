package backend.service;

import backend.dto.ProfileDTO;
import backend.entity.Profile;
import backend.repository.ProfileRepository;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class ProfileService {
    private final String cloudinaryUrl; // Khai báo biến cho URL Cloudinary
    @Autowired
    private ProfileRepository profileRepository;

    // Khởi tạo Dotenv và lấy giá trị từ .env
    public ProfileService() {
        Dotenv dotenv = Dotenv.load();
        this.cloudinaryUrl = dotenv.get("CLOUDINARY_URL"); // Lấy giá trị từ .env
    }

    public Profile findProfileByUserId(Long userId) {
        return profileRepository.findProfileByUserId(userId);
    }

    public Profile createProfileWithAvatar(ProfileDTO profileDTO) {
        // Khởi tạo Cloudinary
        Cloudinary cloudinary = new Cloudinary(cloudinaryUrl);

        // Xử lý upload avatar
        @SuppressWarnings("unchecked")
        Map<String, Object> params = ObjectUtils.asMap(
                "use_filename", true,
                "unique_filename", false,
                "overwrite", true
        );

        try {
            // Upload avatar lên Cloudinary
            @SuppressWarnings("unchecked")
            Map<String, Object> uploadResult = cloudinary.uploader().upload(profileDTO.getAvatar(), params);
            String avatarUrl = (String) uploadResult.get("url");

            // Tạo profile mới
            Profile profile = new Profile();
            profile.setUserId(profileDTO.getUserId());
            profile.setRealName(profileDTO.getRealName());
            profile.setNickName(profileDTO.getNickname());
            profile.setBio(profileDTO.getBio());
            profile.setAvatar(avatarUrl); // Sử dụng URL từ Cloudinary
            return profileRepository.save(profile);
        } catch (Exception e) {
            throw new RuntimeException("Error uploading avatar: " + e.getMessage());
        }
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