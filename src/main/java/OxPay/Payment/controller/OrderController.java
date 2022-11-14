package OxPay.Payment.controller;

import OxPay.Payment.factory.OrderFactory;
import OxPay.Payment.model.dto.SubmitOrderRequest;
import OxPay.Payment.model.entity.Order;
import OxPay.Payment.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("{orderId}")
    public Order getOrderById(@PathVariable long orderId) {
        return this.orderService.getOrderById(orderId);
    }

    @PostMapping("{orderId}")
    public Order submitOrderById(@PathVariable long orderId, @Valid SubmitOrderRequest submitOrderRequest) {
        final var orderDo = OrderFactory.submitOrder(orderId, submitOrderRequest);
        return this.orderService.submitOrder(orderDo);
    }
}
