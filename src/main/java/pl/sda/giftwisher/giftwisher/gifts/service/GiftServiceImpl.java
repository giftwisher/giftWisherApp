package pl.sda.giftwisher.giftwisher.gifts.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.giftwisher.giftwisher.gifts.exceptions.GiftNotFoundException;
import pl.sda.giftwisher.giftwisher.gifts.exceptions.WebApplicationException;
import pl.sda.giftwisher.giftwisher.gifts.model.GiftStatus;
import pl.sda.giftwisher.giftwisher.gifts.model.dto.GiftDto;
import pl.sda.giftwisher.giftwisher.gifts.model.dto.NewGiftDto;
import pl.sda.giftwisher.giftwisher.gifts.model.entity.GiftEntity;
import pl.sda.giftwisher.giftwisher.gifts.repository.GiftRepository;
import pl.sda.giftwisher.giftwisher.users.model.UserEntity;
import pl.sda.giftwisher.giftwisher.users.repository.UserRepository;
import pl.sda.giftwisher.giftwisher.users.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GiftServiceImpl implements GiftService {

    private final GiftRepository giftRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public GiftServiceImpl(GiftRepository giftRepository, UserService userService, UserRepository userRepository) {
        this.giftRepository = giftRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public void addGift(NewGiftDto newGiftDto, String username) {
        GiftEntity giftEntity = GiftEntity.builder()
                .name(newGiftDto.getName())
                .description(newGiftDto.getDescription())
                .giftStatus(GiftStatus.AVAILABLE)
                .occassion(newGiftDto.getOccassion())
                .user(userRepository.findByUsername(username))
                .build();
        GiftEntity entitySaved = giftRepository.save(giftEntity);
        userService.addGift(entitySaved, username);
    }

    @Override
    public List<GiftDto> getAllGifts() {
        return giftRepository.findAll().stream()
                .map(GiftEntity::mapToGiftDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void remove(Long idGift, String username) {
        Optional<GiftEntity> productEntityById = giftRepository.findProductEntityById(idGift);
        giftRepository.removeGiftEntityById(idGift);
        UserEntity userEntity = userRepository.findByUsername(username);
        productEntityById.ifPresent(giftEntity -> userEntity.getGifts().remove(giftEntity));
        userRepository.save(userEntity);
    }

    @Override
    public GiftDto getGiftById(Long idGift) throws WebApplicationException {
        Optional<GiftEntity> optionalGiftEntity = giftRepository.findProductEntityById(idGift);
        return optionalGiftEntity
                .map(GiftEntity::mapToGiftDto)
                .orElseThrow(() -> new GiftNotFoundException("Not found product with id = " + idGift));
    }
}
