package ek.osnb.demo.coffeeshop.order;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
class OrderController {

    private final OrderService service;

    OrderController(OrderService service) {
        this.service = service;
    }

    // Checkout: turns a cart into an order
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    OrderResponse placeOrder(@Valid @RequestBody PlaceOrderRequest request, Authentication auth) {
        return service.placeOrder(request, auth.getName());
    }

    // GET /api/orders?customerId=1 - newest first
    @GetMapping
    List<OrderSummaryResponse> findByCustomer(Authentication auth) {
        return service.findByCustomer(auth.getName());
    }

    @GetMapping("/{id}")
    OrderResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("/{id}/cancel")
    OrderResponse cancel(@PathVariable Long id) {
        return service.cancel(id);
    }

    // Should only be allowed for staff - a job for Spring Security later
    @PostMapping("/{id}/ship")
    OrderResponse ship(@PathVariable Long id) {
        return service.ship(id);
    }
}
