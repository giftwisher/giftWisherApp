package pl.sda.giftwisher.giftwisher.gifts.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sda.giftwisher.giftwisher.gifts.model.GiftStatus;
import pl.sda.giftwisher.giftwisher.gifts.model.dto.GiftDto;
import pl.sda.giftwisher.giftwisher.gifts.model.dto.NewGiftDto;
import pl.sda.giftwisher.giftwisher.gifts.model.entity.GiftEntity;
import pl.sda.giftwisher.giftwisher.gifts.repository.GiftRepository;
import pl.sda.giftwisher.giftwisher.users.model.UserEntity;
import pl.sda.giftwisher.giftwisher.users.repository.UserRepository;
import pl.sda.giftwisher.giftwisher.users.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
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

    public GiftDto getGiftById(Long idGift) {
        return giftRepository.findProductEntityById(idGift)
                .map(g -> g.mapToGiftDto())
                .orElseThrow(() -> new NoSuchElementException("Not found product with id = " + idGift));
    }

    @Override
    @Transactional
    public void updateGiftStatus(Long giftId, GiftStatus giftStatus) {
        giftRepository.updateGiftStatusWhereId(giftStatus, giftId);
    }

    private void setStatus(GiftDto dto) {
        Optional<GiftEntity> productEntityById = giftRepository.findProductEntityById(dto.getId());
        if (productEntityById.isPresent()) {
            productEntityById.ifPresent(entity -> entity.setGiftStatus(dto.getGiftStatus()));
            productEntityById.ifPresent(giftRepository::save);
            log.info(productEntityById.toString());
        }
    }
}
