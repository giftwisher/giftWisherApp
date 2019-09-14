package pl.sda.giftwisher.giftwisher.gifts.exceptions;

public class GiftNotFoundException extends WebApplicationException {

    public GiftNotFoundException(String message) {
        super(message);
    }
}
