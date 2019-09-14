package pl.sda.giftwisher.giftwisher.gifts.service;

import pl.sda.giftwisher.giftwisher.gifts.exceptions.WebApplicationException;
import pl.sda.giftwisher.giftwisher.gifts.model.GiftStatus;
import pl.sda.giftwisher.giftwisher.gifts.model.dto.GiftDto;
import pl.sda.giftwisher.giftwisher.gifts.model.dto.NewGiftDto;

import java.util.List;

public interface GiftService {

    void addGift(NewGiftDto newGiftDto);

    List<GiftDto> getAllGifts();

    void remove(Long idGift);

    GiftDto getGiftById(Long idGift) throws WebApplicationException;
}
