package backend.controller;

import backend.entity.Account;
import backend.service.JWTService;

import backend.service.AccountService;
import backend.service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class AuthController {
    @Autowired
    private JWTService jwtService;
    @Autowired
    private AccountService accountService;
    private Account account = new Account();


    @Autowired
    private EmailService emailService;

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
            } else if (accountService.findAccountByEmail(account.getEmail()) == null) {
                String verifyCode = accountService.generateVerifyCode();
                accountService.createAccount(account.getFirstName(), account.getLastName(), account.getEmail(), verifyCode);
                System.out.println("create account success");
                emailService.sendVerificationEmail(account.getEmail(), verifyCode);

                return ResponseEntity.ok(Map.of("redirectUrl", String.format("/verify/%s", email)));
            } else if (!accountService.findAccountByEmail(account.getEmail()).getIsEmailVerified()) {
                return ResponseEntity.ok(Map.of("redirectUrl", String.format("/verify/%s", email)));
            }
            Account ac = accountService.findAccountByEmail(account.getEmail());
            Map<String, String> response = new HashMap<>();
            String token = JWTService.generateAccessToken(account.getEmail(), account.getFirstName(), account.getLastName(),ac.getAccountId());
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









