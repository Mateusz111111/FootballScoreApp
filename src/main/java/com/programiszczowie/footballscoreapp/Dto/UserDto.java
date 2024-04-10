package com.programiszczowie.footballscoreapp.Dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {

    private Long userid;

    private String email;

    private String password;

    private String firstName;

    private String lastName;
}
