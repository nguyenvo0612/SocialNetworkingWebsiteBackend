package backend.service;

import backend.dto.LastMessageDTO;
import backend.dto.MessagesDTO;
import backend.entity.Account;
import backend.entity.Conversations;
import backend.entity.Messages;
import backend.repository.MessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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


    public List<LastMessageDTO> getConversationsWithPartner(Long accountId) {
        List<Object[]> results = messagesRepository.findConversationsWithPartnerByAccountId(accountId);
        List<LastMessageDTO> dtos = new ArrayList<>();

        for (Object[] row : results) {
            LastMessageDTO dto = new LastMessageDTO();
            dto.setConversationId(((Number) row[0]).longValue());
            dto.setPartnerName((String) row[1]);
            dto.setLastMessage((String) row[2]);
            dto.setLastMessageTime(row[3] != null ? ((Timestamp) row[3]).toLocalDateTime() : null);
            dto.setLastMessageSenderId(row[4] != null ? ((Number) row[4]).longValue() : null);
            dtos.add(dto);
        }

        return dtos;
    }
}
