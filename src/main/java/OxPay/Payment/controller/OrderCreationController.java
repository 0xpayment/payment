package OxPay.Payment.controller;

import OxPay.Payment.service.OrderService;
import OxPay.Payment.util.UriUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/link")
public class OrderCreationController {

    private final OrderService orderService;

    @GetMapping("/{linkId}")
    public ResponseEntity<Void> createOrderForLink(@PathVariable final Long linkId) {
        Long orderId = this.orderService.crateOrderForLink(linkId);
        return ResponseEntity.status(HttpStatus.FOUND).location(UriUtil.createGetOrderByIDUri(orderId)).build();
    }
}
