package account.customValidators;

import account.model.Payment;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class PositiveSalaryListConstraintValidator implements  ConstraintValidator<
       PositiveSalaryListConstraint, List<Payment> > { //PositiveSalaryListConstraint
    @Override
    public boolean isValid(List<Payment> payments, ConstraintValidatorContext context) {
        return payments.stream().noneMatch(payment -> payment.getSalary() < 0);

    }
}
