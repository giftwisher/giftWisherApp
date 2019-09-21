package pl.sda.giftwisher.giftwisher.users.service;

import pl.sda.giftwisher.giftwisher.gifts.model.dto.GiftDto;
import pl.sda.giftwisher.giftwisher.gifts.model.entity.GiftEntity;
import pl.sda.giftwisher.giftwisher.users.dto.RegistrationFormDTO;
import pl.sda.giftwisher.giftwisher.users.model.UserEntity;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void save(RegistrationFormDTO registrationFormDTO);

    UserEntity findByUsername(String username);
    UserEntity findByUuid(UUID uuid);
    void addGift(GiftEntity giftEntity, String username);
    List<GiftDto> getGifts(String username);

}
