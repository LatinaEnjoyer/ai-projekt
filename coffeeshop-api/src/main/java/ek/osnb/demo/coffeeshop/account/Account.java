package ek.osnb.demo.coffeeshop.account;

import jakarta.persistence.*;

@Entity
class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    protected Account() {

    }

    protected Account(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    Long getId() {
        return id;
    }

    protected String getEmail() {
        return email;
    }

    protected String getPassword() {
        return password;
    }

    protected String getRole() {
        return role;
    }
}
