package pl.sda.giftwisher.giftwisher.users.service;

import pl.sda.giftwisher.giftwisher.users.dto.RegistrationFormDTO;
import pl.sda.giftwisher.giftwisher.users.model.UserEntity;

public interface UserService {
    void save(RegistrationFormDTO registrationFormDTO);

    UserEntity findByUsername(String username);
}
