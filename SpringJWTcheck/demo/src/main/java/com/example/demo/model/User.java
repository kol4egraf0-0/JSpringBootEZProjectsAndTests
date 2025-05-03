package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails; //security!!!

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "users")
@Getter
@Setter
//хотя @NoArgsConstructor есть в data,
// и он перестаёт генерировать пустой конструктор,
// если есть другой конструктор
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private boolean enabled;

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "verification_expiration")
    private LocalDateTime verificationCodeExpiresAt;

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { //нужен Spring Security чтобы понимать какие права есть у пользователя
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; //Не просрочен ли аккаунт (например, если срок действия истёк).
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; //Не заблокирован ли аккаунт (например, после 5 неудачных попыток входа).
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; //Не пора ли сменить пароль (например, если пароль старый).
    }

    @Override
    public boolean isEnabled() {
        return enabled; //Активен ли пользователь (например, если админ временно отключил аккаунт).
    }
}
