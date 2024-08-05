package account.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment")
public class Payment {
    private static final String emailRegex = ".+@acme.com";//"\\w+(@acme.com)$"
    private static final String periodRegex = ".+@acme.com";//"\\w+(@acme.com)$"




    @Id
    @Column(name = "payment_id") // user_id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private long id;

    /*@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id") //verificar..."user_id"
    private User user;*/


    @Column(name = "employee")
    // @Email(regexp = emailRegex, message = "Employee must end with '@acme.com'")/ @NotEmpty(message = "Employee should not be empty")
    private String employee;

   // @Id
    @Column(name = "period")
    @NotEmpty(message = "Period should not be empty")
    private String period;

    @Column(name = "salary")
    //  @Min(value = 1, message = "Salary should not be empty")
    private long salary;
}
