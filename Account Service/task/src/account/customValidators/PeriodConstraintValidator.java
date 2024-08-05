package account.customValidators;

import account.model.Payment;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PeriodConstraintValidator implements ConstraintValidator<
        PeriodConstraint, Payment> {
    String periodRegex = "([1][0-2]|0[1-9])-[0-9]{1,4}"; //

    @Override
    public boolean isValid(Payment payment, ConstraintValidatorContext context) {
        return payment.getPeriod().matches(periodRegex);

    }
}
