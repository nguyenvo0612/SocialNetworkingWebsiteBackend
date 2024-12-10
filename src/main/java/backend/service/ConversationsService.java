package backend.service;

import backend.dto.ConversationsDTO;
import backend.entity.Account;
import backend.entity.Conversations;
import backend.repository.ConversationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationsService {
    @Autowired
    private ConversationsRepository conversationsRepository;

    @Autowired
    private AccountService accountService;

    public List<Conversations> getAllConversations() {
        return conversationsRepository.findAll();
    }

    public Conversations getConversationById(Long conversationId) {
        return conversationsRepository.findConversationsByConversationId();
    }

    public Conversations createConversation(ConversationsDTO conversationsDTO) {
        Conversations conversations = new Conversations();
        Account account1 = accountService.findAccountByAccountId(conversationsDTO.getAccountId1());
        Account account2 = accountService.findAccountByAccountId(conversationsDTO.getAccountId2());
        conversations.setAccountId1(account1);
        conversations.setAccountId2(account2);
        return conversationsRepository.save(conversations);
    }
}
