package pl.sda.giftwisher.giftwisher.gifts.model.entity;

import lombok.*;
import pl.sda.giftwisher.giftwisher.gifts.model.GiftStatus;
import pl.sda.giftwisher.giftwisher.gifts.model.Occassion;
import pl.sda.giftwisher.giftwisher.gifts.model.dto.GiftDto;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "GiftEntity")
public class GiftEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private GiftStatus giftStatus;

    @Column(name = "Occassion")
    private Occassion occassion;

    public GiftDto mapToGiftDto() {
        return GiftDto.builder()
                .id(this.id)
                .name(this.name)
                .description(this.description)
                .giftStatus(this.giftStatus)
                .occassion(this.occassion)
                .build();
    }
}
