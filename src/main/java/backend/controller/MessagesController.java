package backend.controller;

import backend.dto.LastMessageDTO;
import backend.dto.MessageBoxDTO;
import backend.dto.MessagesDTO;
import backend.entity.Messages;
import backend.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
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

    @GetMapping("/last-messages/{accountId}")
    public List<LastMessageDTO> getAllConversationsLastMessages(@PathVariable Long accountId) {
        return messagesService.getConversationsWithPartner(accountId);
    }
    @GetMapping("/messages-box/{conversationId}")
    public List<MessageBoxDTO> getAllMessagesByConversationId(@PathVariable Long conversationId) {
        return messagesService.getMessagesBoxByConversationId(conversationId);
    }

}
