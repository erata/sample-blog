package blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class UserNameOrPasswordNotMatch extends RuntimeException {

    public UserNameOrPasswordNotMatch(String message) {
        super(message);
    }

}
