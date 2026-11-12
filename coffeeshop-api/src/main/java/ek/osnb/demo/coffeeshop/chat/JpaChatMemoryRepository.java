package ek.osnb.demo.coffeeshop.chat;

import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;

import java.util.ArrayList;
import java.util.List;

@Repository
class JpaChatMemoryRepository implements ChatMemoryRepository {

    private final ChatMessageJpaRepository repository;

    JpaChatMemoryRepository(ChatMessageJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findConversationIds() {
        return repository.findDistinctConversationIds();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Message> findByConversationId(String conversationId) {

        return repository
                .findByConversationIdOrderByPositionAsc(conversationId)
                .stream()
                .map(this::toMessage)
                .toList();
    }

    @Override
    @Transactional
    public void saveAll(String conversationId, List<Message> messages) {

        repository.deleteByConversationId(conversationId);
        repository.flush();

        List<ChatMessageEntity> entities = new ArrayList<>();

        for (int i = 0; i < messages.size(); i++) {

            Message message = messages.get(i);

            entities.add(ChatMessageEntity.of(
                    conversationId,
                    i,
                    message.getMessageType().name(),
                    message.getText()
            ));
        }

        repository.saveAll(entities);
    }

    @Override
    @Transactional
    public void deleteByConversationId(
            String conversationId) {

        repository.deleteByConversationId(conversationId);
    }

    private Message toMessage(ChatMessageEntity entity) {

        return switch (MessageType.valueOf(
                entity.getRole())) {

            case USER ->
                    new UserMessage(entity.getContent());

            case ASSISTANT ->
                    new AssistantMessage(entity.getContent());

            case SYSTEM ->
                    new SystemMessage(entity.getContent());

            default ->
                    throw new IllegalArgumentException(
                            "Unsupported message type: "
                                    + entity.getRole()
                    );
        };
    }
}
