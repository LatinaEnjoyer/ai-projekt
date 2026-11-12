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
    private final StaffTools staffTools;
    private final CustomerApi customerApi;

    ChatController(ChatClient chatClient, CatalogTools catalogTools, OrderTools orderTools, CustomerApi customerApi, StaffTools staffTools) {
        this.chatClient = chatClient;
        this.catalogTools = catalogTools;
        this.orderTools = orderTools;
        this.customerApi = customerApi;
        this.staffTools = staffTools;
    }


    @PostMapping("/chat")
    ChatResponse chat(@RequestBody ChatRequest request, Authentication auth) {

        Long customerId = customerApi.findCustomerByEmail(auth.getName())
                .map(CustomerInfo::id)
                .orElse(null);

        Map<String, Object> toolContext = customerId == null
                ? Map.of()
                : Map.of("customerId", customerId);

        boolean isStaff = auth.getAuthorities().stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("STAFF"));

        Object[] tools = isStaff
                ? new Object[]{catalogTools, orderTools, staffTools}
                : new Object[]{catalogTools, orderTools};

        String response = chatClient.prompt()
                .system("""
                    You are the coffee shop assistant.

                    Always use the available tools when answering
                    questions about products, prices, stock or orders.

                    Never invent products, prices, stock levels
                    or order information.

                    Only provide information returned by the tools.

                    You cannot create, modify or cancel orders.

                    If you cannot find the requested information
                    say so.
                    """)
                .user(request.message())
                .tools(tools)
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
