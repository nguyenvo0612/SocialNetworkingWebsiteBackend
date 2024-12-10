package backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class MessagesDTO {
    private Long conversation;

    private Long sender;

    private String content;
}
