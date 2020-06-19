package com.example.account.model;

import com.example.account.dto.UserRole;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Entity
public class Account extends BaseEntity implements Serializable {


    @Column(nullable = false ,name = "user_name")
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String email;

    @Column(nullable = false)
    @Enumerated
    private UserRole role;

    @Column(nullable = false, name = "a_non_expired")
    private boolean accountNonExpired;

    @Column(nullable = false, name = "non_locked")
    private boolean accountNonLocked;

    @Column(nullable = false, name = "c_non_expired")
    private boolean credentialsNonExpired;

    @Column(nullable = false)
    private boolean enabled;

}