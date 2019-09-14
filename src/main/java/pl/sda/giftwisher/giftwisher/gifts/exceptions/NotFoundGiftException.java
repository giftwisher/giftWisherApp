package pl.sda.giftwisher.giftwisher.gifts.exceptions;

public class NotFoundGiftException extends WebApplicationException {

    public NotFoundGiftException(String message) {
        super(message);
    }
}
