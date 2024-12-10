package backend.controller;

import backend.dto.MessagesDTO;
import backend.entity.Messages;
import backend.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    @GetMapping("/getall/{conversationId}")
    public Messages getAllMessages(@PathVariable Long conversationId) {
        return messagesService.getAllMessagesByConversationId(conversationId);
    }

    @PostMapping("/create")
    public Messages createMessage(@RequestBody MessagesDTO messagesDTO) {
        return messagesService.createMessage(messagesDTO);
    }
}
