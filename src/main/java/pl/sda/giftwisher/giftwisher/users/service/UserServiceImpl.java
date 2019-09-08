package pl.sda.giftwisher.giftwisher.users.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sda.giftwisher.giftwisher.users.dto.RegistrationFormDTO;
import pl.sda.giftwisher.giftwisher.users.model.UserEntity;
import pl.sda.giftwisher.giftwisher.users.repository.RoleRepository;
import pl.sda.giftwisher.giftwisher.users.repository.UserRepository;

import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void save(RegistrationFormDTO registrationFormDTO) {
        UserEntity user=new UserEntity();
        user.setUsername(registrationFormDTO.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(registrationFormDTO.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
