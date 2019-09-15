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
import pl.sda.giftwisher.giftwisher.users.service.UserService;

import java.security.Principal;

@Controller
public class GiftController {

    private final GiftService giftService;
    private final UserService userService;

    public GiftController(GiftService giftService, UserService userService) {
        this.giftService = giftService;
        this.userService = userService;
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
    public ModelAndView addNewGift(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("gift_form");
        NewGiftDto newGiftDto = new NewGiftDto();
        modelAndView.addObject("gift", newGiftDto);
        modelAndView.addObject("Occassion", Occassion.values());
        modelAndView.addObject("GiftStatus", GiftStatus.values());
        modelAndView.addObject("gifts", userService.getGifts(principal.getName()));
        return modelAndView;
    }

    @PostMapping("/gift_save")
    public ModelAndView saveGift(@ModelAttribute("gift") NewGiftDto giftToSave, ModelAndView modelAndView, Principal principal) {
        modelAndView.setViewName("gift_form");
        giftService.addGift(giftToSave, principal.getName());
        return addNewGift(principal);
    }

    @GetMapping("/gifts/{idGift}/delete")
    public ModelAndView deleteGift(@PathVariable Long idGift, Principal principal) throws WebApplicationException {
        giftService.remove(idGift, principal.getName());
        return new ModelAndView("redirect:/gift_form");
    }
}
