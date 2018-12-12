package com.example.stanislav.myapplication.entity.model;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class UpdatePasswordModel {

    private Long id;
    private String oldPassword;
    private String newPassword;

}
