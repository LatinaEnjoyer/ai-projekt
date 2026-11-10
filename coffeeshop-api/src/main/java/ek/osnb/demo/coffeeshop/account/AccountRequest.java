package ek.osnb.demo.coffeeshop.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

record AccountRequest(
        @NotNull
        @Email
        String email,
        @NotNull
        String password
) {
}
