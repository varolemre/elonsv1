package com.artnft.artnft.dto;

import com.artnft.artnft.valitor.annotations.UniqueMail;
import com.artnft.artnft.valitor.annotations.UniqueUsername;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserRegisterDto {
    @Email(message = "Invalid e-mail address")
    @UniqueMail
    private String mail;

    @UniqueUsername
    @Size(min = 5, max = 55, message = "Username must be between 5 to 55 characters")
    @NotBlank(message = "Username can not be blank")
    private String username;

    @Size(min = 3, max = 55, message = "Display name must be longer than 3 to 55 characters")
    @NotBlank(message = "Display name can not be blank")
    private String displayName;

    private String refCode;

    @Size(min = 6)
    @NotEmpty(message = "Password can not be empty")
    private char[] password;
}
