package ek.osnb.demo.coffeeshop.account;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
class JpaUserDetailsService implements UserDetailsService {
    private final AccountRepository repo;

    JpaUserDetailsService(AccountRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));

        return User
                .withUsername(account.getEmail())
                .password(account.getPassword())
                .roles(account.getRole())
                .build();
    }
}
