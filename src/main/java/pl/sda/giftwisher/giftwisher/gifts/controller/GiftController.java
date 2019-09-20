package pl.sda.giftwisher.giftwisher.gifts.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.sda.giftwisher.giftwisher.gifts.model.GiftStatus;
import pl.sda.giftwisher.giftwisher.gifts.model.Occassion;
import pl.sda.giftwisher.giftwisher.gifts.model.dto.GiftDto;
import pl.sda.giftwisher.giftwisher.gifts.model.dto.NewGiftDto;
import pl.sda.giftwisher.giftwisher.gifts.service.GiftService;
import pl.sda.giftwisher.giftwisher.gifts.validator.NewGiftValidator;
import pl.sda.giftwisher.giftwisher.users.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;

@Slf4j
@Controller
public class GiftController {

    private final GiftService giftService;
    private final UserService userService;
    private final NewGiftValidator giftValidator;

    public GiftController(GiftService giftService, UserService userService, NewGiftValidator giftValidator) {
        this.giftService = giftService;
        this.userService = userService;
        this.giftValidator = giftValidator;
    }

    @GetMapping("/wishlist/{username}")
    public String getWishlist(@PathVariable String username, Model model) {
        log.info("list of gifts from " + username + " has been accessed");
        model.addAttribute("gifts", userService.getGifts(username));
        model.addAttribute("statuses", GiftStatus.values());
        model.addAttribute("username", username);
        return "show_gifts";
    }

    @PostMapping("/wishlist/{username}/save")
    public String saveWishlist(@PathVariable String username, @ModelAttribute ArrayList<GiftDto> gifts) {
        giftService.saveChanges(gifts, username);
        log.info("list of gifts from " + username + " has been saved:" + gifts.toString());
        return "redirect:/wishlist/"+username;
    }

    @GetMapping("/giftForm")
    public String addNewGift(Model model, Principal principal, HttpServletRequest request) {
        NewGiftDto newGiftDto = new NewGiftDto();
        if (!model.containsAttribute("gift")) {
            model.addAttribute("gift", newGiftDto);
        }
        model.addAttribute("Occassion", Occassion.values());
        model.addAttribute("gifts", userService.getGifts(principal.getName()));
        //path is not working correctly if you run your app from localhost, but it should work from heroku
        model.addAttribute("path", getLinkToWishlist(principal, request));
        return "gift_form";
    }

    private String getLinkToWishlist(Principal principal, HttpServletRequest request) {
        String path = request.getRequestURL().toString();
        path = path.substring(0, path.length() - 8).concat("wishlist/" + principal.getName());
        return path;
    }

    @PostMapping("/gift_save")
    public String saveGift(@ModelAttribute("gift") NewGiftDto giftToSave,
                           Principal principal, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        giftValidator.validate(giftToSave, bindingResult);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.gift", bindingResult);
            redirectAttributes.addFlashAttribute("gift", giftToSave);
            return "redirect:/giftForm";
        }
        giftService.addGift(giftToSave, principal.getName());
        return "redirect:/giftForm";
    }

    @GetMapping("/gifts/{idGift}/delete")
    public ModelAndView deleteGift(@PathVariable Long idGift, Principal principal) {
        giftService.remove(idGift, principal.getName());
        return new ModelAndView("redirect:/giftForm");
    }
}
