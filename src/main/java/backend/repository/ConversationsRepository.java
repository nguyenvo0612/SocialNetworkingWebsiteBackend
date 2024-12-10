package backend.repository;

import backend.entity.Conversations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationsRepository extends JpaRepository<Conversations, Long> {
 Conversations findByConversationId(Long conversationId);


}
