package ek.osnb.demo.coffeeshop.chat;

import jakarta.persistence.*;

@Entity
class ChatMessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String conversationId;

    @Column(nullable = false)
    private Integer position;

    @Column(nullable = false)
    private String role;

    @Lob
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String content;

    protected ChatMessageEntity() {}

    private ChatMessageEntity(String conversationId, Integer position, String role, String content) {
        this.conversationId = conversationId;
        this.position = position;
        this.role = role;
        this.content = content;
    }

    public static ChatMessageEntity of(String conversationId, Integer position, String role, String content) {
        return new ChatMessageEntity(conversationId, position, role, content);
    }

    public Long getId() {
        return id;
    }

    public String getConversationId() {
        return conversationId;
    }

    public Integer getPosition() {
        return position;
    }

    public String getRole() {
        return role;
    }

    public String getContent() {
        return content;
    }
}
