package pl.sda.giftwisher.giftwisher.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.giftwisher.giftwisher.users.model.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

}
