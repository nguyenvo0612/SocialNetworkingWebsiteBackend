package backend.service;

import backend.dto.ProfileDTO;
import backend.entity.Profile;
import backend.repository.ProfileRepository;
import io.github.cdimascio.dotenv.Dotenv;

import java.security.SecureRandom;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class ProfileService {
    private final String cloudinaryUrl; 
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 12;
    @Autowired
    private ProfileRepository profileRepository;

    // Khởi tạo Dotenv và lấy giá trị từ .env
    public ProfileService() {
        Dotenv dotenv = Dotenv.load();
        this.cloudinaryUrl = dotenv.get("CLOUDINARY_URL"); // Lấy giá trị từ .env
    }

    public Profile findProfileByAccountId(Long accountId) {
        return profileRepository.findProfileByAccountId(accountId);
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
            profile.setProfileId(profileDTO.getAccountId());
            profile.setRealName(profileDTO.getRealName());
            profile.setNickName(profileDTO.getNickName());
            profile.setBio(profileDTO.getBio());
            profile.setAvatar(avatarUrl); // Sử dụng URL từ Cloudinary
            return profileRepository.save(profile);
        } catch (Exception e) {
            throw new RuntimeException("Error uploading avatar: " + e.getMessage());
        }
    }

    public Profile updateProfile(Long profileId, ProfileDTO profileDTO) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new RuntimeException("Profile not found"));
        profile.setNickName(profileDTO.getNickName());
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

    public static String generateVerifyCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder verifyCode = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length()); 
            verifyCode.append(CHARACTERS.charAt(index)); 
        }
        return verifyCode.toString(); 
    }
}