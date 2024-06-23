package com.sar.psapp.model;

import com.sar.psapp.commons.UserRole;
import com.sar.psapp.commons.states.UserState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "app_user_name")
    private String userName;

    @Column(name = "app_user_email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "app_user_state")
    private UserState state;

    @Enumerated(EnumType.STRING)
    @Column(name = "app_user_role")
    private UserRole role;

}
