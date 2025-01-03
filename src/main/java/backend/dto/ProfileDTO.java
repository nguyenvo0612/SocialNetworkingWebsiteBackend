package backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDTO {

    private String nickName;

    private String realName;

    private String bio;

    private String location;

    private String avatar;

    private String linkWebsite;

    private Long accountId;
}
