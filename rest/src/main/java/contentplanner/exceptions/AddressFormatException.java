package contentplanner.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Aynulin on 14.11.2016.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class AddressFormatException extends RuntimeException {
    public AddressFormatException() {
        super("wrong address format");
    }
}
