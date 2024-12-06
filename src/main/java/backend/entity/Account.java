package backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @Column(name = "verify_code")
    private Boolean isEmailVerified;

    @Column(name = "verification_token")
    private String verificationToken;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role roleId;

}
