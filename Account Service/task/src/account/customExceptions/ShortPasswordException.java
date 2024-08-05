package account.customExceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Password length must be 12 chars minimum!") //, reason = "User exist!"
@Data

public class ShortPasswordException extends  RuntimeException {
    public static String message = "Password length must be 12 chars minimum!";

    public ShortPasswordException() {
        super();
    }

}
