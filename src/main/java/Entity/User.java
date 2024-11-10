package Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table (name = "user")
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String fname;
    private String mname;
    private String lname;
    private String username;
    private String password;
    private Date birthday;
    private Long role_id;
}
