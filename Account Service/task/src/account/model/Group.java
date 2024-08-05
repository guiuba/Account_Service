package account.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Data
//@Table(name = "groups") //"group"
public class Group {
    //removed getter and setter to save space

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE) //IDENTITY
    @JsonIgnore
    private Long id;

    //   @Column(unique = true, nullable = false)
    //  private String code;
    @Column(name = "name")
    private String name;

    @JsonIgnore
    @Column(name = "isBusiness")
    private boolean isBusiness;

    @JsonIgnore
    @Column(name = "isAdministrative")
    private boolean isAdministrative;

/*    @JsonIgnore
 //   @ManyToMany(mappedBy = "userGroups") //user_group  roles ""
    private Set<User> users;  //userSet*/

  /*  public Group(String name) {
        this.name = name;
        users = new HashSet<>();

        if ("ROLE_ADMINISTRATOR".equals(name)) {
            isBusiness = false;
            isAdministrative = true;
        }

        if ("ROLE_USER".equals(name) || "ROLE_ACCOUNTANT".equals(name) || "ROLE_AUDITOR".equals(name)) {
            isBusiness = true;
            isAdministrative = false;
        }
    }*/

}
