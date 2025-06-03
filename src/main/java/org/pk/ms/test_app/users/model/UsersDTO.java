package org.pk.ms.test_app.users.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class UsersDTO {

    private Long id;

    @NotNull
    @Size(max = 30)
    @UsersUsernameUnique
    private String username;

    @NotNull
    @Size(max = 15)
    private String password;

    @NotNull
    @Size(max = 255)
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

}
