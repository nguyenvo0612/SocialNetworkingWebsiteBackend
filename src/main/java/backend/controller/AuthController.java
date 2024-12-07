package backend.controller;

import backend.entity.Account;
import backend.service.JWTService;

import backend.service.AccountService;
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
public class AuthController {
    @Autowired
    private JWTService jwtService;
    @Autowired
    private AccountService userService;
    private Account account = new Account();

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


    @GetMapping("/auth/api")
    public ResponseEntity<Map<String, String>> getToken(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal authentication) {
        if (authentication != null) {
            Account account = new Account();
            account.setEmail(authentication.getAttribute("email"));
            account.setFirstName(authentication.getAttribute("family_name"));
            account.setLastName(authentication.getAttribute("given_name"));
            String email = authentication.getAttribute("email");
            if (email == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Email is missing"));
            } else if (userService.findAccountByEmail(account.getEmail()) == null) {
                userService.createAccount(account.getFirstName(), account.getLastName(), account.getEmail());
                System.out.println("create account success");
            }
            Map<String, String> response = new HashMap<>();
            String token = JWTService.generateAccessToken(account.getEmail(), account.getFirstName(), account.getLastName());
            String refreshToken = JWTService.generateRefreshToken(account.getEmail());
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









