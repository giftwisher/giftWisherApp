package pl.sda.giftwisher.giftwisher.gifts.exceptions;

public class GiftNotFoundException extends IllegalAccessException {

    public GiftNotFoundException(String message) {
        super(message);
    }
}
