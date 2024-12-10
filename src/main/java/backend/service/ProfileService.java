package backend.service;

import backend.dto.ProfileDTO;
import backend.entity.Account;
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

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private AccountService accountService;

    // Khởi tạo Dotenv và lấy giá trị từ .env
    public ProfileService() {
        Dotenv dotenv = Dotenv.load();
        this.cloudinaryUrl = dotenv.get("CLOUDINARY_URL"); // Lấy giá trị từ .env
    }

    public Profile findProfileByAccountId(Long accountId) {
        return profileRepository.findProfileByAccountId(accountId);
    }

    public Long findProfileIdByAccountId(Long accountId) {
        Profile profile = findProfileByAccountId(accountId);
        return profile.getProfileId();
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
            Account account=accountService.findAccountByAccountId(profileDTO.getAccountId());
            Profile profile = new Profile();
            profile.setAccount(account);
            profile.setRealName(profileDTO.getRealName());
            profile.setNickName(profileDTO.getNickName());
            profile.setBio(profileDTO.getBio());
            profile.setLinkWebsite(profileDTO.getLinkWebsite());
            profile.setLocation(profileDTO.getLocation());
            profile.setAvatar(avatarUrl); // Sử dụng URL từ Cloudinary
            return profileRepository.save(profile);
        } catch (Exception e) {
            throw new RuntimeException("Error uploading avatar: " + e.getMessage());
        }
    }

    public Profile updateProfile( ProfileDTO profileDTO) {
        Profile profile = new Profile();
        profile.setProfileId(findProfileIdByAccountId(profileDTO.getAccountId()));
        profile.setAccount(accountService.findAccountByAccountId(profileDTO.getAccountId()));
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


}