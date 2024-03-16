package com.example.NaquelesDias.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.NaquelesDias.model.user.authentication.UserRole;

import java.util.Collection;
import java.util.List;

@Table(name = "user")
@Entity(name = "User")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstname;
    private String lastname;
    private int cpf;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private int biologicalInfoId;
    private int addressInfoId;

    public User(String firstname, String lastname, int cpf, String email, String password,
                UserRole role, int biologicalInfoId, int addressInfoId) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.role = role;
        this.biologicalInfoId = biologicalInfoId;
        this.addressInfoId = addressInfoId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) return List.of(
                new SimpleGrantedAuthority("user"),
                new SimpleGrantedAuthority("admin"));
        else return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}