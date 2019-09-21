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
import pl.sda.giftwisher.giftwisher.users.model.UserEntity;
import pl.sda.giftwisher.giftwisher.users.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

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

    @GetMapping("/wishlist/{uuid}")
    public String getWishlist(@PathVariable UUID uuid, Model model) {
        UserEntity userEntity = userService.findByUuid(uuid);
        log.info("list of gifts from " + userEntity.getUsername() + " has been accessed");
        List<GiftDto> gifts = userService.getGifts(userEntity.getUsername());
        gifts.sort(Comparator.comparing(GiftDto::getGiftStatus));
        model.addAttribute("gifts", gifts);
        model.addAttribute("statuses", GiftStatus.values());
        model.addAttribute("uuid", uuid);
        return "show_gifts";
    }

    @PostMapping("/wishlist/{uuid}/gifts/{giftId}/status/{giftStatus}")
    public String saveWishlist(
            @PathVariable UUID uuid,
            @PathVariable Long giftId,
            @PathVariable GiftStatus giftStatus) {
        giftService.updateGiftStatus(giftId, giftStatus);
        return "redirect:/wishlist/" + uuid;
    }

    @GetMapping("/giftForm")
    public String addNewGift(Model model, Principal principal, HttpServletRequest request) {
        NewGiftDto newGiftDto = new NewGiftDto();
        if (!model.containsAttribute("gift")) {
            model.addAttribute("gift", newGiftDto);
        }
        model.addAttribute("Occassion", Occassion.values());
        model.addAttribute("gifts", userService.getGifts(principal.getName()));
        model.addAttribute("path", getLinkToWishlist(principal, request));
        return "gift_form";
    }

    private String getLinkToWishlist(Principal principal, HttpServletRequest request) {
        String path = request.getRequestURL().toString();
        UUID uuid = userService.findByUsername(principal.getName()).getCorrId();
        path = path.substring(0, path.length() - 8).concat("wishlist/" + uuid);
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
