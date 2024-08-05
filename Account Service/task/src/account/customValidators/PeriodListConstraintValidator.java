package account.customValidators;

import account.model.Payment;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.*;

public class PeriodListConstraintValidator implements ConstraintValidator<
        PeriodListConstraint, List<Payment>> {
    String periodRegex = "([1][0-2]|0[1-9])-[0-9]{1,4}"; //

    @Override
    public boolean isValid(List<Payment> payments, ConstraintValidatorContext context) {
        boolean isPeriodsInValidFormat = payments.stream()
                .allMatch(payment -> payment.getPeriod().matches(periodRegex));

        boolean hasDuplicatePeriods = false;

        Map<String, List<String>> paymentRecords = new HashMap<>();

        for (int i = 0; i < payments.size(); i++) {
            String employee = payments.get(i).getEmployee();
            String period = payments.get(i).getPeriod();
            if (!paymentRecords.containsKey(employee)) {
                paymentRecords.put(employee, List.of(period));
            } else {
                List<String> periods = new ArrayList<>(paymentRecords.get(employee));
                if (periods.contains(period)) {
                    hasDuplicatePeriods = true;
                } else {
                    periods.add(period);
                    paymentRecords.replace(employee, periods);
                }
            }
        }
        return isPeriodsInValidFormat && !hasDuplicatePeriods;
        // payments.forEach(payment -> paymentRecords.put(payment.getEmployee(), List.of(payment.getPeriod())) );


     //   Set<String> uniquePeriods = new HashSet<>();//payments.stream().filter(payment -> payment.getPeriod()).collect(Collectors.toSet());
     //   payments.forEach(payment -> uniquePeriods.add(payment.getPeriod()));

      //  return isPeriodsInValidFormat && (uniquePeriods.size() == payments.size());
    }
}
