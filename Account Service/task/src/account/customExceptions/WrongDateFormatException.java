package account.customExceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Wrong date!") //
@Data
public class WrongDateFormatException extends RuntimeException{
    public static String message = "Wrong date!";

    public WrongDateFormatException() {
    }
}
