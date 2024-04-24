package com.programiszczowie.footballscoreapp.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LoginResponse {

    private String message;
    private Boolean status;
}
