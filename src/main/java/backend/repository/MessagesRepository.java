package backend.repository;

import backend.entity.Conversations;
import backend.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessagesRepository extends JpaRepository<Messages, Long> {
    Messages findMessagesByConversation(Conversations conversation);

    @Query(value = """
            WITH RankedMessages AS (
                SELECT 
                    c.conversation_id,
                    a.last_name as partner_name,
                    m.content,
                    m.created_at,
                    m.sender,
                    ROW_NUMBER() OVER (PARTITION BY c.conversation_id ORDER BY m.created_at DESC) as rn
                FROM conversations c
                LEFT JOIN messages m ON c.conversation_id = m.conversation
                LEFT JOIN account a ON (
                    CASE 
                        WHEN c.account_id1 = :accountId THEN c.account_id2
                        ELSE c.account_id1
                    END = a.account_id
                )
                WHERE c.account_id1 = :accountId OR c.account_id2 = :accountId
            )
            SELECT * FROM RankedMessages WHERE rn = 1
            """, nativeQuery = true)
    List<Object[]> findConversationsWithPartnerByAccountId(Long accountId);
}
