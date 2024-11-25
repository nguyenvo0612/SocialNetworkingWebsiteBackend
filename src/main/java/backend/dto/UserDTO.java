package backend.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

public class UserDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String picture;
    private Integer roleId;

}
