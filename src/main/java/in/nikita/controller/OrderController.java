package in.nikita.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import in.nikita.entity.Orders;
import in.nikita.entity.UserRegisterEntity;
import in.nikita.service.CartService;
import in.nikita.service.OrderService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    // ---------------- Place multiple orders ----------------
    @PostMapping("/add")
    public Map<String, Object> placeOrder(@RequestBody List<Map<String, Object>> orderItems, HttpSession session) {
        UserRegisterEntity user = (UserRegisterEntity) session.getAttribute("customer");
        if (user == null) {
            return Map.of("status", "error", "message", "User not logged in");
        }

        // orderItems is a list of objects: {productId, quantity}
        boolean success = orderService.placeOrders(user, orderItems);

        if(success) {
            return Map.of("status", "success", "message", "Order placed successfully");
        } else {
            return Map.of("status", "error", "message", "Failed to place order");
        }
    }
  

}
