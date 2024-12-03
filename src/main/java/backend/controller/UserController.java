package backend.controller;

import backend.entity.User;
import backend.service.UserService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/getall")
    public ResponseEntity<List<User>> getAll() {
        List<User> userList= userService.getAll();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/find-userid/{email}")
    public Long findUserByEmail(@PathVariable String email) {
        User user = userService.findUserByEmail(email);
        return user.getUserId();
    }
}
