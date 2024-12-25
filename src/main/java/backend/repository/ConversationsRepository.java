package backend.repository;

import backend.entity.Conversations;
import org.hibernate.query.NativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationsRepository extends JpaRepository<Conversations, Long> {
 Conversations findByConversationId(Long conversationId);


 @Query(value = """
select c.conversation_id, c.account_id1, c.account_id2, m.sender, m.content , m.created_at  from conversations c inner join messages m on c.conversation_id = m.conversation where c.conversation_id=: conversationId
""", nativeQuery = true)
 List<Object[]> getMessagesBoxById(Long conversationId);
}
