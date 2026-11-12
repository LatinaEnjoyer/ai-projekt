package ek.osnb.demo.coffeeshop.chat;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/conversations")
class ConversationController {
    private final ConversationQuery conversationQuery;

    ConversationController(ConversationQuery conversationQuery) {
        this.conversationQuery = conversationQuery;
    }

    @GetMapping
    List<String> getConversationIds() {
        return conversationQuery.getConversationIds();
    }

    @GetMapping("/{conversationId}")
    List<ConversationMessageResponse> getConversationById(@PathVariable String conversationId) {
        return conversationQuery.getConversationByConversationId(conversationId);
    }
}
