package backend.service;

import backend.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public boolean findProfileByUserId(Long userId) {
        if (profileRepository.findByUserId(userId) == null) {
            return false;
        }return true;
    }
}
