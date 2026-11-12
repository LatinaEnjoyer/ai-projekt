package ek.osnb.demo.coffeeshop.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface ChatMessageJpaRepository extends JpaRepository<ChatMessageEntity, Long> {

    List<ChatMessageEntity> findByConversationIdOrderByPositionAsc(String conversationId);

    void deleteByConversationId(String conversationId);

    @Query("""
        SELECT DISTINCT m.conversationId
        FROM ChatMessageEntity m
        """)
    List<String> findDistinctConversationIds();
}
