package account.customExceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CustomErrorMessage  { //extends ResponseEntityExceptionHandler

    // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDate timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    public CustomErrorMessage() {

    }
}
