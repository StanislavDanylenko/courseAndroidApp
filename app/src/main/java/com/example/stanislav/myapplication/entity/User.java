package com.example.stanislav.myapplication.entity;

import com.example.stanislav.myapplication.entity.enumeration.Localization;
import com.example.stanislav.myapplication.entity.enumeration.RoleUser;
import com.example.stanislav.myapplication.entity.enumeration.TypeOfUser;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor
@ToString
public class User {

    private Long id;
    private String email;

    private String firstName;
    private String lastName;
    private String patronymic;
    private Long defaultPopulatedPoint;

    private Boolean isActive;

    private String password;

    private Localization localization;
    private TypeOfUser type;
    private Set<RoleUser> roles;

}
