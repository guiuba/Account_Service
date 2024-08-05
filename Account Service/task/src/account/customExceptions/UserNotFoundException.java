package account.customExceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User not found!") //
@Data
public class UserNotFoundException extends RuntimeException{
    public static String message = "User not found!";

    public UserNotFoundException() { //String message

        super(); //message
    }
}
