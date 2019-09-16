package pl.sda.giftwisher.giftwisher.gifts.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.sda.giftwisher.giftwisher.gifts.model.Occassion;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewGiftDto {

    private String name;
    private String description;
    private Occassion occassion;
}
