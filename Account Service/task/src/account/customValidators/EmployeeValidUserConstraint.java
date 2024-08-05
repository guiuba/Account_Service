package account.customValidators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = EmployeeValidUserConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface EmployeeValidUserConstraint {
    String message() default "Employee is not a user of Account service!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
