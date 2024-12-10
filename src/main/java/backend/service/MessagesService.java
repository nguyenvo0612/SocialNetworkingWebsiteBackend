package backend.service;

import backend.dto.MessagesDTO;
import backend.entity.Account;
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

    @Autowired
    private AccountService accountService;

    public Messages getAllMessagesByConversationId(Long conversationId) {
        Conversations conversation = conversationsService.getById(conversationId);
        return messagesRepository.findMessagesByConversation(conversation);
    }

    public Messages createMessage(MessagesDTO messagesDTO) {
        Messages messages = new Messages();
        Conversations conversation = conversationsService.getById(messagesDTO.getConversation());
        Account sender =accountService.findAccountByAccountId(messagesDTO.getSender());
        messages.setConversation(conversation);
        messages.setSender(sender);
        messages.setContent(messagesDTO.getContent());
        System.out.println(conversation.getConversationId());
        System.out.println(sender.getAccountId());
        return messagesRepository.save(messages);
    }
}
