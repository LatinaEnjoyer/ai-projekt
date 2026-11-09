package ek.osnb.demo.coffeeshop.security;

import ek.osnb.demo.coffeeshop.customer.CustomerApi;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
class JpaUserDetailsService implements UserDetailsService {
    private final CustomerApi repo;

    JpaUserDetailsService(CustomerApi repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    }
}
