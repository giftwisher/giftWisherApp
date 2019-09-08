package pl.sda.giftwisher.giftwisher.users.dto;

import lombok.Data;

@Data
public class RegistrationFormDTO {
    private String username;
    private String password;
    private String repeatedPassword;
}
