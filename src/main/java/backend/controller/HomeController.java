package backend.controller;

import backend.entity.User;
import backend.service.JWTService;

import backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {
    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserService userService;
    private User user = new User();

//
//    @GetMapping("/refresh")
//    public ResponseEntity<Map<String, String>> refreshAccessToken(@RequestBody Map<String, String> request) {
//        String refreshToken = request.get("refreshToken");
//
//        // Validate refreshToken
//        if (jwtService.validateToken(refreshToken)) {
//            String email = JWTService.extractUsername(refreshToken);
//            String newAccessToken = JWTService.generateAccessToken( "userId",email, "firstName", "lastName", "picture");
//            return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid refresh token"));
//        }
//    }


    @GetMapping("/auth/callback")
    public ResponseEntity<Map<String, String>> getToken(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal authentication) {
        if (authentication != null) {
            User user = new User();
            user.setEmail(authentication.getAttribute("email"));
            user.setFirstName(authentication.getAttribute("family_name"));
            user.setLastName(authentication.getAttribute("given_name"));
            user.setPicture(authentication.getAttribute("picture"));
            String email = authentication.getAttribute("email");
            if (email == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Email is missing"));
            } else if (userService.findUserByEmail(user.getEmail()) == null) {
                userService.createUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPicture());
                System.out.println("create user success");
            }
            Map<String, String> response = new HashMap<>();
            String token = JWTService.generateAccessToken(user.getEmail(), user.getFirstName(), user.getLastName(), user.getPicture());
            String refreshToken = JWTService.generateRefreshToken(user.getEmail());
            return ResponseEntity.ok(Map.of("accessToken", token, "refreshToken", refreshToken));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "User is not authenticated"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        // Xóa phiên làm việc
        request.getSession().invalidate();

        // Nếu sử dụng JWT, có thể thêm logic để làm cho token không còn hiệu lực
        // Ví dụ: thêm token vào danh sách đen (blacklist)

        return ResponseEntity.ok().build();
    }
}









