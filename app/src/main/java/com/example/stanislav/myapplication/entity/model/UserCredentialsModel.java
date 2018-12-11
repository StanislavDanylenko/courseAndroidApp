package com.example.stanislav.myapplication.entity.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class UserCredentialsModel {

    private String email;
    private String password;

}
