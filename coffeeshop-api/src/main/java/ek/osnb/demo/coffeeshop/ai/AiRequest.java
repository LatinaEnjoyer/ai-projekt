package ek.osnb.demo.coffeeshop.ai;

import java.util.List;

record AiRequest(String model, List<Message> messages, int max_tokens) {
    public record Message(String role, String content) {}
}
