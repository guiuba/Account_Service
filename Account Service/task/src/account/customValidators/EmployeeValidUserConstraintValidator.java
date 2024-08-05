package account.customValidators;

import account.dao.UserRepo;
import account.model.Payment;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EmployeeValidUserConstraintValidator implements ConstraintValidator<
        EmployeeValidUserConstraint, List<Payment>> {
    @Autowired
    UserRepo userRepo;

    @Override
    public boolean isValid(List<Payment> payments, ConstraintValidatorContext context) {
        return payments.stream().allMatch(payment ->userRepo.existsUserByEmailIgnoreCase(payment.getEmployee()));

    }
}
