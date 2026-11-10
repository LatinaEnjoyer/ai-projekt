package ek.osnb.demo.coffeeshop.account;

import ek.osnb.demo.coffeeshop.common.ConflictException;
import ek.osnb.demo.coffeeshop.customer.CustomerApi;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }


    AccountResponse create(AccountRequest accountRequest) {
        if (accountRepository.existsByEmail(accountRequest.email())) {
            throw new ConflictException("Email already exists");
        }

        Account account = accountRepository.save(new Account(
                accountRequest.email(),
                passwordEncoder.encode(accountRequest.password()),
                "CUSTOMER"
        ));
        return AccountMapper.toResponse(account);
    }
}
