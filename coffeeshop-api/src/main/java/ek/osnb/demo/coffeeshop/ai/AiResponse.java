package ek.osnb.demo.coffeeshop.ai;

import java.util.List;

record AiResponse(List<Choice> choices) {
    record Choice(Message message) {
        record Message(String role, String content) {}
    }
}
