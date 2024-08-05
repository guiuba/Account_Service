package account.controllers;

import account.customExceptions.BadPasswordException;
import account.customExceptions.CustomErrorMessage;
import account.customExceptions.NegativeSalaryException;
import account.customExceptions.WrongDateFormatException;
import account.customValidators.*;
import account.model.Payment;
import account.services.BusinessFunctionalityService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@Validated // testing uploadPayroll
@ControllerAdvice
@RequestMapping()
public class BusinessFunctionality {
    private BusinessFunctionalityService businessFunctionalityService;

    @Autowired
    public BusinessFunctionality(BusinessFunctionalityService businessFunctionalityService) {
        this.businessFunctionalityService = businessFunctionalityService;
    }

    @ExceptionHandler({BadPasswordException.class,
            NegativeSalaryException.class,
            WrongDateFormatException.class,
            NegativeSalaryException.class,

    })
    @GetMapping(path = "/api/empl/payment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEmployeesPayrolls(
            Exception ex, WebRequest request,
        //    @PeriodConstraint
         //   @PeriodListConstraint
            @AuthenticationPrincipal UserDetails details,
            @RequestParam(value = "period", required = false, defaultValue = "no_period") String period
    ) {
        return businessFunctionalityService.getEmployeesPayrolls(details, ex, request, period);
    }



    @Transactional(rollbackFor = {NegativeSalaryException.class,
            WrongDateFormatException.class})
    @PostMapping(value = "/api/acct/payments",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadPayroll(
            Exception ex, WebRequest request,
            @RequestBody
            //    @NotEmpty(message = "Input Payments list cannot be empty.")
            @PositiveSalaryListConstraint
            @PeriodListConstraint
            @EmployeeValidUserConstraint
            List<@Valid Payment> payments //
    ) {

        return businessFunctionalityService.uploadPayroll(ex, request, payments);
    }

    @Transactional(rollbackFor = {NegativeSalaryException.class,
            WrongDateFormatException.class})
    @PutMapping(value = "/api/acct/payments",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatesPaymentInfo(
            @PositiveSalaryConstraint
            @PeriodConstraint
          //  @EmployeeValidUserConstraint
            @Valid @RequestBody  Payment payment
    ) {

        return businessFunctionalityService.updatesPaymentInfo(payment);
    }

    @PostMapping("/actuator/shutdown")
    public ResponseEntity<?> shutDown() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handle(ConstraintViolationException constraintViolationException,
                                    WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();
        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
        String errorMessage = "";
        if (!violations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            violations.forEach(violation -> builder.append(
                    " " + violation.getMessage()));
            //" " + propertyIndex(violation.getPropertyPath().toString()) +
            errorMessage = builder.toString();
        } else {
            errorMessage = "ConstraintViolationException occurred.";
        }
        //    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(new CustomErrorMessage(
                LocalDate.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errorMessage,
                path),
                HttpStatus.BAD_REQUEST);
    }

    String propertyIndex(String property) {
        return "[" + property.substring(property.length() - 1) + "]";
    }

}
