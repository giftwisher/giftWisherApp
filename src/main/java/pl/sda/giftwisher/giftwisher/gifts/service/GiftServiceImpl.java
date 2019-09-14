package pl.sda.giftwisher.giftwisher.gifts.service;

import org.springframework.stereotype.Service;
import pl.sda.giftwisher.giftwisher.gifts.exceptions.GiftNotFoundException;
import pl.sda.giftwisher.giftwisher.gifts.exceptions.WebApplicationException;
import pl.sda.giftwisher.giftwisher.gifts.model.dto.GiftDto;
import pl.sda.giftwisher.giftwisher.gifts.model.dto.NewGiftDto;
import pl.sda.giftwisher.giftwisher.gifts.model.entity.GiftEntity;
import pl.sda.giftwisher.giftwisher.gifts.repository.GiftRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiftServiceImpl implements GiftService {

    private final GiftRepository giftRepository;

    public GiftServiceImpl(GiftRepository giftRepository) {
        this.giftRepository = giftRepository;
    }

    @Override
    public void addGift(NewGiftDto newGiftDto) {
        GiftEntity giftEntity = GiftEntity.builder()
                .name(newGiftDto.getName())
                .description(newGiftDto.getDescription())
                .giftStatus(newGiftDto.getGiftStatus())
                .occassion(newGiftDto.getOccassion())
                .build();
        giftRepository.save(giftEntity);
    }

    @Override
    public List<GiftDto> getAllGifts() {
        return giftRepository.findAll().stream()
                .map(GiftEntity::mapToGiftDto)
                .collect(Collectors.toList());
    }

    @Override
    public void remove(Long idGift) {
        giftRepository.removeGiftEntityById(idGift);
    }

    @Override
    public GiftDto getGiftById(Long idGift) throws WebApplicationException {
        Optional<GiftEntity> optionalGiftEntity = giftRepository.findProductEntityById(idGift);
        return optionalGiftEntity
                .map(GiftEntity::mapToGiftDto)
                .orElseThrow(() -> new GiftNotFoundException("Not found product with id = " + idGift));
    }
}
