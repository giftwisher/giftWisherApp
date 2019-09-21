package pl.sda.giftwisher.giftwisher.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.giftwisher.giftwisher.users.model.UserEntity;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
    UserEntity findByCorrId(UUID uuid);
}
