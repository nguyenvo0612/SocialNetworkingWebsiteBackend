package backend.service;

import backend.entity.Account;
import backend.entity.Role;
import backend.repository.RoleRepository;
import backend.repository.AccountRepository;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 12;

    public Account createAccount(String firstName, String lastName, String email, String verificationToken) {
        Account account = new Account();
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setEmail(email);
        account.setVerificationToken(verificationToken);
        account.setIsEmailVerified(false);
        Role role =roleRepository.findById(1).orElse(null);
        account.setRoleId(role);
        return accountRepository.save(account);
    }

    public Account findAccountByEmail(String email) {
        return accountRepository.findAccountByEmail(email);
    }
    public Account verifyAccount(String email,String verificationToken) {
        Account account = findAccountByEmail(email);
        if (account != null && account.getVerificationToken().equals(verificationToken)) {
            account.setIsEmailVerified(true);
            account.setVerificationToken(null);
            return accountRepository.save(account);
        }
        return null;
    }

    public List<Account> getAll(){
        return accountRepository.findAll();
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
