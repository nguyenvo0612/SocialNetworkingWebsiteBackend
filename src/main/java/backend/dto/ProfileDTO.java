package backend.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDTO {

    private String nickname;

    private String realName;

    private String bio;

    private String location;

    private String avatar;

    private Long userId;
}
