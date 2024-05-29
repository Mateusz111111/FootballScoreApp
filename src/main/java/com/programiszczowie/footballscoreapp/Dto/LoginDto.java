package com.programiszczowie.footballscoreapp.Dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginDto {

    private String email;
    private String password;

}
