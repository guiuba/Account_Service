package account.services;

import account.customExceptions.CustomErrorMessage;
import account.customExceptions.NegativeSalaryException;
import account.customExceptions.UnauthorizedException;
import account.customExceptions.WrongDateFormatException;
import account.dao.PaymentsRepo;
import account.dao.PayrollRepo;
import account.dao.UserRepo;
import account.mapper.Mapper;
import account.model.Payment;
import account.model.Payroll;
import account.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
//@Validated
@Component
@Transactional
public class BusinessFunctionalityService {
    private final UserRepo userRepo;
    private final PaymentsRepo paymentsRepo;
    private final PayrollRepo payrollRepo;
    private final Mapper mapper;
    private final UnauthorizedException unauthorizedException;


    @Autowired
    public BusinessFunctionalityService(UserRepo userRepo, PaymentsRepo paymentsRepo, PayrollRepo payrollRepo, Mapper mapper, UnauthorizedException unauthorizedException) {
        this.userRepo = userRepo;
        this.paymentsRepo = paymentsRepo;
        this.payrollRepo = payrollRepo;
        this.mapper = mapper;
        this.unauthorizedException = unauthorizedException;
    }

    public ResponseEntity<?> getEmployeesPayrolls(UserDetails userDetails,
                                                  Exception ex,
                                                  WebRequest request, String period) {

        Optional<User> user = userRepo.findUserByEmailIgnoreCase(userDetails.getUsername()); //getPassword()
        //   if (user.isPresent()) {
        if ("no_period".equals(period)) {
            //  return ResponseEntity.ok("sem parametros");
            return ResponseEntity.ok(payrollRepo.findAllByEmailIgnoreCaseOrderBySalaryIdDesc(user.get().getEmail()));
        }
        if (isPeriodFormatOk(period)) {
            return ResponseEntity.ok(payrollRepo.findByEmailIgnoreCaseAndSalaryId(user.get().getEmail(),
                    Long.valueOf(period.substring(0, 2))));
        } else {
            String path = ((ServletWebRequest) request).getRequest().getRequestURI();
            return new ResponseEntity<>(new CustomErrorMessage(
                    LocalDate.now(),
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    WrongDateFormatException.message,
                    path),
                    HttpStatus.BAD_REQUEST);
        }
     //   }

        //    return ResponseEntity.ok("o parametro é: " + period);
           /*
            UserDto userDto = mapper.toUserDto(user.get());
            return ResponseEntity.ok(userDto);*/
        //     }
   /*     return new ResponseEntity<>(new CustomErrorMessage(
                LocalDate.now(),
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                UnauthorizedException.message,
                ((ServletWebRequest) request).getRequest().getRequestURI()),
                HttpStatus.UNAUTHORIZED);*/
    }


    @Transactional(rollbackFor = {
            NegativeSalaryException.class,
            WrongDateFormatException.class})
    public ResponseEntity<?> uploadPayroll(Exception ex, WebRequest request, List<Payment> payments) { //, List<Payments> payments
        //    Optional<User> user = userRepo.findUserByEmailIgnoreCase(userDetails.getUsername());
        payments.forEach(payment -> {
            Optional<User> user = userRepo.findUserByEmailIgnoreCase(payment.getEmployee());
            if (user.isPresent()) {
                Payroll payroll = new Payroll();
                payroll.setName(user.get().getName());
                payroll.setLastname(user.get().getLastname());
                //     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

                //    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");
                //    YearMonth ym = YearMonth.parse(payment.getPeriod(), formatter);
                //   LocalDate localDate = LocalDate.parse(payment.getPeriod(), formatter);
                //     String month = ym.getMonth().toString().charAt(0) + ym.getMonth().toString().substring(1).toLowerCase();
                //    payroll.setPeriod(ym);
                String formattedDate = formatDateToWord(payment.getPeriod());
                payroll.setPeriod(formattedDate);
                //   payroll.setPeriod(String.valueOf(String.format("%s-%s", month, ym.getYear())));
                //   payroll.setPeriod(ym.getMonth().toString().toLowerCase());
                //  payroll.setPeriod(ym.format(DateTimeFormatter.ofPattern("Month-yyyy")));

                //   String dollars = String.valueOf(payment.getSalary() / 100);
                //   String cents = String.valueOf(payment.getSalary() % 100);
                payroll.setSalary(formatSalaryToWord(payment.getSalary())); //String.format("%s dollar(s) %s cent(s)",dollars, cents)

                //  payroll.setSalary(String.format("%f dollar(s) %f cent(s)", payment.getSalary() / 100, payment.getSalary() % 100));
                payroll.setEmail(payment.getEmployee());
                payroll.setSalaryId(Long.valueOf(payment.getPeriod().substring(0, 2)));

                payrollRepo.save(payroll);

            }

        });
        paymentsRepo.saveAll(payments);
        return ResponseEntity.ok(Map.of("status", "Added successfully!"));
    }


    @Transactional(rollbackFor = {
            NegativeSalaryException.class,
            WrongDateFormatException.class
    })
    public ResponseEntity<?> updatesPaymentInfo(Payment payment) {
        //  List<Payroll> payrollList = payrollRepo.findAllByEmailIgnoreCase(payment.getEmployee());
        Optional<Payment> paymentToUpdate = paymentsRepo.findByEmployeeIgnoreCaseAndAndPeriod(
                payment.getEmployee(), payment.getPeriod());
        paymentToUpdate.ifPresent(value -> {
            value.setSalary(payment.getSalary());
            paymentsRepo.save(paymentToUpdate.get());
        });
        Optional<Payroll> payrollToUpdate = payrollRepo.findByEmailIgnoreCaseAndPeriod(
                payment.getEmployee(), formatDateToWord(payment.getPeriod()));
        payrollToUpdate.ifPresent(value -> {
            value.setSalary(formatSalaryToWord(payment.getSalary()));
        });


        return ResponseEntity.ok(Map.of("status", "Updated successfully!"));
    }

    String formatDateToWord(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");
        YearMonth ym = YearMonth.parse(date, formatter);
        String month = ym.getMonth().toString().charAt(0) + ym.getMonth().toString().substring(1).toLowerCase();
        return String.valueOf(String.format("%s-%s", month, ym.getYear()));
    }

    String formatSalaryToWord(long salary) {
        String dollars = String.valueOf(salary / 100);
        String cents = String.valueOf(salary % 100);
        return String.format("%s dollar(s) %s cent(s)",
                dollars, cents);
    }

    boolean isPeriodFormatOk(String date) {
        String periodRegex = "([1][0-2]|0[1-9])-[0-9]{1,4}";
        return date.matches(periodRegex);
    }


}
