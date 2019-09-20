package pl.sda.giftwisher.giftwisher.gifts.model.entity;

import lombok.*;
import pl.sda.giftwisher.giftwisher.gifts.model.GiftStatus;
import pl.sda.giftwisher.giftwisher.gifts.model.Occassion;
import pl.sda.giftwisher.giftwisher.gifts.model.dto.GiftDto;
import pl.sda.giftwisher.giftwisher.users.model.UserEntity;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "Gift")
public class GiftEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;

    @Enumerated(value = EnumType.STRING)
    private GiftStatus giftStatus;

    @Enumerated(value = EnumType.STRING)
    private Occassion occassion;

    @ManyToOne
    private UserEntity user;

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
