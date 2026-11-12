package ek.osnb.demo.coffeeshop.ai;

import ek.osnb.demo.coffeeshop.customer.CustomerApi;
import ek.osnb.demo.coffeeshop.customer.CustomerInfo;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/assistant")
class ChatController {
    private final ChatClient chatClient;

    private final CatalogTools catalogTools;
    private final OrderTools orderTools;
    private final CustomerApi customerApi;

    ChatController(ChatClient chatClient, CatalogTools catalogTools, OrderTools orderTools, CustomerApi customerApi) {
        this.chatClient = chatClient;
        this.catalogTools = catalogTools;
        this.orderTools = orderTools;
        this.customerApi = customerApi;
    }

    @PostMapping("/chat")
    ChatResponse chat(@RequestBody ChatRequest request, Authentication auth) {

        Long customerId = customerApi.findCustomerByEmail(auth.getName())
                .map(CustomerInfo::id)
                .orElse(null);

        Map<String, Object> toolContext = customerId == null
                ? Map.of()
                : Map.of("customerId", customerId);

        String response = chatClient.prompt()
                .user(request.message())
                .tools(catalogTools, orderTools)
                .toolContext(toolContext)
                .advisors(advisor -> advisor.param(
                        ChatMemory.CONVERSATION_ID,
                        auth.getName()
                ))
                .call()
                .content();

        return new ChatResponse(response);
    }
}
