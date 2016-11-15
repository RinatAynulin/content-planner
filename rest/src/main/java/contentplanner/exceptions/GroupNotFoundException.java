package contentplanner.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Aynulin on 14.11.2016.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class GroupNotFoundException extends RuntimeException {
    public GroupNotFoundException(int groupId) {
        super("could not find group     '" + groupId + "'.");
    }
}
