package com.programiszczowie.footballscoreapp.Dto;


import lombok.*;

@Builder
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

    public UserDto(String email, String password, String test, String user) {
    }
}
