package pl.sda.giftwisher.giftwisher.gifts.model.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import pl.sda.giftwisher.giftwisher.gifts.model.GiftStatus;
import pl.sda.giftwisher.giftwisher.gifts.model.Occassion;
import pl.sda.giftwisher.giftwisher.gifts.model.entity.GiftEntity;
import pl.sda.giftwisher.giftwisher.users.repository.UserRepository;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GiftDto {

    private Long id;
    private String name;
    private String description;
    private GiftStatus giftStatus;
    private Occassion occassion;

}
