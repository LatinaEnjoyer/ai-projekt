package ek.osnb.demo.coffeeshop.ai;

import ek.osnb.demo.coffeeshop.order.OrderApi;
import ek.osnb.demo.coffeeshop.order.OrderInfo;
import ek.osnb.demo.coffeeshop.order.OrderSummaryInfo;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;
import org.springframework.ai.chat.model.ToolContext;

import java.util.List;

@Component
class OrderTools {

    private final OrderApi orderApi;

    OrderTools(OrderApi orderApi) {
        this.orderApi = orderApi;
    }

    @Tool(description = "List the customer's own orders, newest first")
    public List<OrderSummaryInfo> findOrders(ToolContext context) {
        return orderApi.findOrders((Long) context.getContext().get("customerId"));
    }

    @Tool(description = "Find specific order")
    public OrderInfo getOrder(ToolContext context, Long orderId) {
        return orderApi.getOrder((Long) context.getContext().get("customerId"), orderId);
    }
}
