package ek.osnb.demo.coffeeshop.ai;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ChatController {
    private final AiClientService aiClientService;

    ChatController(AiClientService aiClientService) {
        this.aiClientService = aiClientService;
    }

    @GetMapping("/ask")
    String chat(@RequestParam String prompt) {
        return aiClientService.chat(prompt);
    }
}
