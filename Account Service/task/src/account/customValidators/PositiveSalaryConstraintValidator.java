package account.customValidators;

import account.model.Payment;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class PositiveSalaryConstraintValidator implements ConstraintValidator <
        PositiveSalaryConstraint, Payment> {
 /*   @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return false;
    }*/
    @Override
    public boolean isValid(Payment payment, ConstraintValidatorContext context) {

        return  payment.getSalary() > 0;
    }
}
