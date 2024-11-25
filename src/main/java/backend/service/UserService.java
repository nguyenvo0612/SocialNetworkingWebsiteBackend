package backend.service;

import backend.dto.UserDTO;
import backend.entity.Role;
import backend.entity.User;
import backend.repository.RoleRepository;
import backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public User createUser(String firstName,String lastName,String email,String picture) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPicture(picture);
        Role role =roleRepository.findById(1).orElse(null);
        user.setRoleId(role);
        return userRepository.save(user);
    }

    public Boolean findUserByEmail(String email) {
        if (userRepository.findByEmail(email) == null) {
            return false;
        }
        return true;
    }
}
