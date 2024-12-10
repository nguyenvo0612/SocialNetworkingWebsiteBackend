package backend.repository;

import backend.entity.Conversations;
import backend.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesRepository extends JpaRepository<Messages, Long> {
    Messages findMessagesByConversation(Conversations conversation);
}
