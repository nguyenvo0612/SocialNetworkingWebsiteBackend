package backend.controller;

import backend.dto.ConversationsDTO;
import backend.entity.Conversations;
import backend.service.ConversationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversations")
public class ConversationsController {
    @Autowired
    private ConversationsService conversationsService;

    @GetMapping("/getall")
    public List<Conversations> getAllConversations() {
        return conversationsService.getAllConversations();
    }

    @PostMapping("/create")
    public Conversations createConversation(@RequestBody ConversationsDTO conversationsDTO) {
        return conversationsService.createConversation(conversationsDTO);
    }
}
