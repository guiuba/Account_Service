package account.customValidators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = PositiveSalaryConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface PositiveSalaryConstraint {
    String message() default "Salary must be non negative!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
