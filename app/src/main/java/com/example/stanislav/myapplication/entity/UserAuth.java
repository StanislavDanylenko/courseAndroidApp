package com.example.stanislav.myapplication.entity;

import com.example.stanislav.myapplication.entity.enumeration.Localization;
import com.example.stanislav.myapplication.entity.enumeration.RoleUser;
import com.example.stanislav.myapplication.entity.enumeration.TypeOfUser;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
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
public class UserAuth {

    private Long id;
    private String email;

    private Long defaultPopulatedPoint;

    @JsonIgnore
    private String password;

    private Localization localization;

    private RoleUser roles;

}
