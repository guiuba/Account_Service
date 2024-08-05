package account.customExceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User exist!") //
@Data
public class UserExistException extends RuntimeException {
 public static String message = "User exist!";

    public UserExistException() { //String message

        super(); //message
    }
}
