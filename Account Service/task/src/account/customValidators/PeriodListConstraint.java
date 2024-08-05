package account.customValidators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = PeriodListConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface PeriodListConstraint {
    String message() default "Wrong period!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
