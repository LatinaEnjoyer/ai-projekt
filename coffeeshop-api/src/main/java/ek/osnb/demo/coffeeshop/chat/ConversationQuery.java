package ek.osnb.demo.coffeeshop.chat;

import java.util.List;

interface ConversationQuery {
    List<String> getConversationIds();
    List<ConversationMessageResponse> getConversationByConversationId(String conversationId);
}
