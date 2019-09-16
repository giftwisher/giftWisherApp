package pl.sda.giftwisher.giftwisher.gifts.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.sda.giftwisher.giftwisher.gifts.model.dto.NewGiftDto;

@Component
public class NewGiftValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return NewGiftDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotEmpty");
    }
}
