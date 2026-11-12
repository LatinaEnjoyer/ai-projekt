package ek.osnb.demo.coffeeshop.order;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
class StaffOrderController {

    private final OrderService orderService;

    StaffOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/staff/orders")
    List<OrderSummaryResponse> findAllOrders() {
        return orderService.findAllOrders();
    }
}
