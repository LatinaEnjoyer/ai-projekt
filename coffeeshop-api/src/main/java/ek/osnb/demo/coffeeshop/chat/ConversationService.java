package ek.osnb.demo.coffeeshop.chat;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ConversationService implements ConversationQuery {
    private final ChatMessageJpaRepository repository;


    ConversationService(ChatMessageJpaRepository repository) {
        this.repository = repository;
    }

    public List<String> getConversationIds() {
        return repository.findDistinctConversationIds();
    }

    public List<ConversationMessageResponse> getConversationByConversationId(String conversationId) {
        return repository.findByConversationIdOrderByPositionAsc(conversationId)
                .stream()
                .map(entity -> new ConversationMessageResponse(entity.getRole(), entity.getContent()))
                .toList();
    }
}
