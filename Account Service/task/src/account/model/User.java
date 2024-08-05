package account.model;

import account.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
//@AllArgsConstructor
@Entity
//@ToString(exclude = "userGroups")
@Data
@Table(name = "users")
public class User {
    private static final String emailRegex = ".+@acme.com";//"\\w+(@acme.com)$"
    @Id
    @Column(name = "user_id") //   id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private long id;

    @Column(name = "name")
    @NotEmpty(message = "Name should not be empty")
    private String name;

    @Column(name = "last_name")
    @NotEmpty(message = "Last name should not be empty")
    private String lastname;


    @Column(name = "email")
    @Email(regexp = emailRegex, message = "Email must end with '@acme.com'")
    @NotEmpty(message = "Email should not be empty")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //  @Size(min = 12, message = "Password length must be 12 chars minimum!")
    @Column(name = "password")
    @NotEmpty(message = "Password should not be empty")
    private String password;

    /*    @ManyToMany(//fetch = FetchType.EAGER,
                cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
        })
        @JoinTable(name = "user_group", //"userGroups"
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "group_id"
                ))*/
    //  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    //   @ToString.Exclude
    @ElementCollection(fetch = FetchType.EAGER, targetClass = UserRole.class)
  //  @Builder.Default
 //   @JsonIgnore
    @Enumerated(EnumType.STRING)
    public Set<UserRole> roles = new HashSet<>(); //userGroups

 /*   public void addGroup(Group group) {
        userGroups.add(group);
        group.getUsers().add(this); //
    }*/

    public User(User userRequest, String password) {
        this.password = password;
        this.name = userRequest.getName();
        this.email = userRequest.getEmail();
        this.lastname = userRequest.getLastname();
        this.name = userRequest.getEmail();
    }


//private Set<Group> userGroups = new HashSet<>();
//@Transient

//@Column(name = "userGroups")

//private Set<String> userGroups;



  /*  @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Payment> payments = new ArrayList<>();*/

/*    @Column(name = "role")
    @JsonIgnore

    //  private String role;
    EnumSet<UserRole> userGroups;*/


   /*

    @Column(name = "operation")
    @JsonIgnore
    private String operation;*/


}
