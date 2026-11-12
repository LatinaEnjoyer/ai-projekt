package ek.osnb.demo.coffeeshop.account;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
class AccountInitData implements CommandLineRunner {

    private final AccountRepository accounts;
    private final PasswordEncoder passwordEncoder;

    AccountInitData(AccountRepository accounts, PasswordEncoder passwordEncoder) {
        this.accounts = accounts;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!accounts.existsByEmail("staff@coffeeshop.dk")) {
            accounts.save(new Account(
                    "staff@coffeeshop.dk",
                    passwordEncoder.encode("staff123"),
                    "STAFF"
            ));
        }
    }
}
