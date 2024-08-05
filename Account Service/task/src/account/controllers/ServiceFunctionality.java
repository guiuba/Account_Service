package account.controllers;

import account.dto.UserDto;
import account.model.RoleHandler;
import account.services.ServiceFunctionalityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@ControllerAdvice
public class ServiceFunctionality {
    ServiceFunctionalityService serviceFunctionalityService;

    @Autowired
    public ServiceFunctionality(ServiceFunctionalityService serviceFunctionalityService) {
        this.serviceFunctionalityService = serviceFunctionalityService;
    }

    @PutMapping(path = "/user/role",
            consumes = MediaType.APPLICATION_JSON_VALUE
            , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeUserRole(
            Exception ex, WebRequest request,
            @AuthenticationPrincipal UserDetails details,
            @Valid @RequestBody RoleHandler roleHandler
    ) {

        return serviceFunctionalityService.changeUserRole(ex, request, details, roleHandler);
    }

  //  @ExceptionHandler({ControllerExceptionHandler.class}) //{CustomAccessDeniedHandler.class}

    @DeleteMapping("/user/{userEmail}")
    public ResponseEntity<?> deleteUser(
          //  @Validated
            @PathVariable(name = "userEmail") String userEmail,
            @AuthenticationPrincipal UserDetails userDetails,
            WebRequest request) {
        return serviceFunctionalityService.deleteUser(userEmail, request, userDetails);
    }

    @GetMapping("/user")
    public List<UserDto> getAllUsers() { //String

        return serviceFunctionalityService.getAllUsers();
    }
}
