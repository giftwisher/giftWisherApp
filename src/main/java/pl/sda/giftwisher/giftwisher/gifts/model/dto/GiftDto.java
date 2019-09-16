package pl.sda.giftwisher.giftwisher.gifts.model.dto;

import lombok.*;
import pl.sda.giftwisher.giftwisher.gifts.model.GiftStatus;
import pl.sda.giftwisher.giftwisher.gifts.model.Occassion;

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
