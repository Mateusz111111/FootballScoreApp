package com.programiszczowie.footballscoreapp.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LoginResponse {

    String message;
    Boolean status;
}
