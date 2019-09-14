package pl.sda.giftwisher.giftwisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.sda.giftwisher.giftwisher.gifts.model.dto.NewGiftDto;
import pl.sda.giftwisher.giftwisher.gifts.service.GiftService;

@SpringBootApplication
public class GiftwisherApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(GiftwisherApplication.class, args);
    }


    @Autowired
    private GiftService giftService;

    @Override
    public void run(String... args) throws Exception {

        giftService.addGift(NewGiftDto.builder().name("Just a sample Gift").description("For sample occassion").build());
        giftService.addGift(NewGiftDto.builder().name("Just another sample Gift").description("For another sample occassion").build());
        giftService.addGift(NewGiftDto.builder().name("Just a third sample Gift").description("For every sample occassion").build());
    }
}
