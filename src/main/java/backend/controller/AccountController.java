package backend.controller;

import backend.entity.Account;
import backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class AccountController {
    @Autowired
    private AccountService userService;


    @GetMapping("/getall")
    public ResponseEntity<List<Account>> getAll() {
        List<Account> accountList = userService.getAll();
        return ResponseEntity.ok(accountList);
    }

    @GetMapping("/find-userid/{email}")
    public Long findUserByEmail(@PathVariable String email) {
        Account account = userService.findAccountByEmail(email);
        return account.getAccountId();
    }
}
