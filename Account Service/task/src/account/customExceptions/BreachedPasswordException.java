package account.customExceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The password is in the hacker's database!")
@Data
public class BreachedPasswordException extends RuntimeException{
    public static String message = "The password is in the hacker's database!";
    public BreachedPasswordException() {
        super();
    }
}
