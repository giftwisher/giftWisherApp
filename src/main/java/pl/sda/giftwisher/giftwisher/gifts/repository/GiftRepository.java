package pl.sda.giftwisher.giftwisher.gifts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.sda.giftwisher.giftwisher.gifts.model.GiftStatus;
import pl.sda.giftwisher.giftwisher.gifts.model.entity.GiftEntity;

import java.util.List;
import java.util.Optional;

public interface GiftRepository extends JpaRepository<GiftEntity, Long> {

    List<GiftEntity> findAll();

    void removeGiftEntityById(Long id);

    Optional<GiftEntity> findProductEntityById(Long id);

    @Modifying
    @Query("update GiftEntity g set g.giftStatus = :giftStatus where g.id=:giftId")
    void updateGiftStatusWhereId(@Param("giftStatus")GiftStatus giftStatus,
                                 @Param("giftId")Long giftId);

}
