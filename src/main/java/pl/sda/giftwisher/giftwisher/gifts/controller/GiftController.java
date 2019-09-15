package pl.sda.giftwisher.giftwisher.gifts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.giftwisher.giftwisher.gifts.exceptions.WebApplicationException;
import pl.sda.giftwisher.giftwisher.gifts.model.GiftStatus;
import pl.sda.giftwisher.giftwisher.gifts.model.Occassion;
import pl.sda.giftwisher.giftwisher.gifts.model.dto.GiftDto;
import pl.sda.giftwisher.giftwisher.gifts.model.dto.NewGiftDto;
import pl.sda.giftwisher.giftwisher.gifts.service.GiftService;

@Controller
public class GiftController {

    private final GiftService giftService;

    public GiftController(GiftService giftService) {
        this.giftService = giftService;
    }

    @GetMapping("/gifts")
    public ModelAndView showGiftsPage(ModelAndView modelAndView) {
        modelAndView.setViewName("show_gifts");
        modelAndView.addObject("gifts", giftService.getAllGifts());
        return modelAndView;
    }

    @GetMapping("/gifts/{idGift}")
    public ModelAndView getOneGift(@PathVariable Long idGift) throws WebApplicationException {
        ModelAndView modelAndView = new ModelAndView("show_one");
        GiftDto giftById = giftService.getGiftById(idGift);
        modelAndView.addObject("gift", giftById);
        return modelAndView;
    }

    @GetMapping("/gift_form")
    public ModelAndView addNewGift() {
        ModelAndView modelAndView = new ModelAndView("gift_form");
        NewGiftDto newGiftDto = new NewGiftDto();
        modelAndView.addObject("gift", newGiftDto);
        modelAndView.addObject("Occassion", Occassion.values());
        modelAndView.addObject("GiftStatus", GiftStatus.values());
        return modelAndView;
    }

    @PostMapping("/gift_form")
    public ModelAndView saveGift(@ModelAttribute("gift") NewGiftDto giftToSave, ModelAndView modelAndView) {
        modelAndView.setViewName("gift_form");
        giftService.addGift(giftToSave);
        NewGiftDto newGiftDto = new NewGiftDto();
        modelAndView.addObject("gift", newGiftDto);
        return modelAndView;
    }
}
