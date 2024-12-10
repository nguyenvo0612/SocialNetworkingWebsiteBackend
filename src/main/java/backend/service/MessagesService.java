package backend.service;

import backend.entity.Conversations;
import backend.entity.Messages;
import backend.repository.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagesService {
    @Autowired
    private MessagesRepository messagesRepository;

    @Autowired
    private ConversationsService conversationsService;

    public Messages findMessagesByConversation(Long conversationId) {
        Conversations conversation = conversationsService.getConversationById(conversationId);
        return messagesRepository.findMessagesByConversation(conversation);
    }
}
