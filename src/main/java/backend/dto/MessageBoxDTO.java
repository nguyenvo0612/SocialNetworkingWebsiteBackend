package backend.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageBoxDTO {
    private Long conversationId;
    private Long accountId1;
    private Long accountId2;
    private Long senderId;
    private String content;
    private LocalDateTime createdAt;
}
