package backend.service;

import backend.entity.Account;
import backend.entity.Role;
import backend.repository.RoleRepository;
import backend.repository.AccountRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;

    public Account createAccount(String firstName, String lastName, String email) {
        Account account = new Account();
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setEmail(email);
        Role role =roleRepository.findById(1).orElse(null);
        account.setRoleId(role);
        return accountRepository.save(account);
    }

    public Account findAccountByEmail(String email) {
        return accountRepository.findAccountByEmail(email);
    }

    public List<Account> getAll(){
        return accountRepository.findAll();
    }



}
