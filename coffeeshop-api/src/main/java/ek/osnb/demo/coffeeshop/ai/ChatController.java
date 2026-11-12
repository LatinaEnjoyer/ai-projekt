package ek.osnb.demo.coffeeshop.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assistant")
class ChatController {
    private final ChatClient chatClient;

    ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @PostMapping("/chat")
    ChatResponse chat(@RequestBody ChatRequest request, Authentication auth) {

        String response = chatClient.prompt()
                .user(request.message())
                .advisors(advisor -> advisor.param(
                        ChatMemory.CONVERSATION_ID,
                        auth.getName()
                ))
                .call()
                .content();

        return new ChatResponse(response);
    }
}
