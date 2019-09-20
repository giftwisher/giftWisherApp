package pl.sda.giftwisher.giftwisher.gifts.service;

import pl.sda.giftwisher.giftwisher.gifts.model.GiftStatus;
import pl.sda.giftwisher.giftwisher.gifts.model.dto.GiftDto;
import pl.sda.giftwisher.giftwisher.gifts.model.dto.NewGiftDto;

import java.util.List;

public interface GiftService {

    void addGift(NewGiftDto newGiftDto, String username);

    List<GiftDto> getAllGifts();

    void remove(Long idGift, String username);

    GiftDto getGiftById(Long idGift);

    void updateGiftStatus(Long giftId, GiftStatus giftStatus);
}
