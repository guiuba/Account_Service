package account.controllers;

import account.customExceptions.BreachedPasswordException;
import account.customExceptions.SamePasswordException;
import account.customExceptions.ShortPasswordException;
import account.customExceptions.UserExistException;
import account.model.PasswordHandler;
import account.model.User;
import account.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

//import javax.validation.Valid;

@RestController
@ControllerAdvice
@RequestMapping("/api/auth")
public class Authentication {
    private AuthenticationService authenticationService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public Authentication(AuthenticationService authenticationService, PasswordEncoder passwordEncoder) {
        this.authenticationService = authenticationService;
        this.passwordEncoder = passwordEncoder;
    }

    @ExceptionHandler({
            UserExistException.class,
            ShortPasswordException.class,
            BreachedPasswordException.class,
            SamePasswordException.class
    })
    @PostMapping(path = "/signup",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signup( //UserDto
                           Exception ex, WebRequest request,
                         //  @Valid
                                         @RequestBody User user) {
        return authenticationService.signup(user, ex, request);
    }
   // @RequestMapping(value = "/changepass", method = RequestMethod.POST)
    @PostMapping(path = "/changepass",
            consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePass(
            Exception ex, WebRequest request,
            @AuthenticationPrincipal UserDetails details,
            //   @Valid
            @RequestBody PasswordHandler passwordHandler

    ) {
        return authenticationService.changePass(details, passwordHandler, ex, request);
    }


}