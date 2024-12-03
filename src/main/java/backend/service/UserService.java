package backend.service;

import backend.entity.Role;
import backend.entity.User;
import backend.repository.RoleRepository;
import backend.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

    public User  findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }
}
