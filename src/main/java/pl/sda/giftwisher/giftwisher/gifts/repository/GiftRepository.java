package pl.sda.giftwisher.giftwisher.gifts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.giftwisher.giftwisher.gifts.model.entity.GiftEntity;

import java.util.List;
import java.util.Optional;

public interface GiftRepository extends JpaRepository<GiftEntity, Long> {

    List<GiftEntity> findAll();

    void removeGiftEntityById(Long id);

    Optional<GiftEntity> findProductEntityById(Long id);
}
