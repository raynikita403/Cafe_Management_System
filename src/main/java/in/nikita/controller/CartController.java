package in.nikita.controller;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import in.nikita.entity.CartItem;
import in.nikita.entity.UserRegisterEntity;
import in.nikita.entity.CafePruduct;
import in.nikita.service.CartService;
import in.nikita.service.CafeProductService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CafeProductService productService;

    // ---------------- Add product to cart ----------------
    @PostMapping("/add/{productId}")
    @ResponseBody
    public Map<String, Object> addToCart(@PathVariable Integer productId, HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        UserRegisterEntity customer = (UserRegisterEntity) session.getAttribute("customer");
        if (customer == null) {
            response.put("status", "error");
            response.put("message", "Login required");
            return response;
        }

        CafePruduct product = productService.getProductById(productId);
        if (product == null) {
            response.put("status", "error");
            response.put("message", "Product not found");
            return response;
        }

        CartItem item = cartService.addToCart(customer, product);

        String base64Image = "";
        if (product.getImage() != null && product.getImage().length > 0) {
            base64Image = "data:image/png;base64," + Base64.getEncoder().encodeToString(product.getImage());
        }

        response.put("cartItemId", item.getId());   // CartItem ID
        response.put("productId", product.getId());
        response.put("name", product.getName());
        response.put("price", product.getPrice());
        response.put("image", base64Image);
        response.put("status", "added");

        return response;
    }

    // ---------------- Get cart items as JSON ----------------
    @GetMapping("/items")
    @ResponseBody
    public List<Map<String, Object>> getCartItems(HttpSession session) {
        UserRegisterEntity customer = (UserRegisterEntity) session.getAttribute("customer");
        if (customer == null) return List.of();

        List<CartItem> cartItems = cartService.getCartItems(customer);

        return cartItems.stream().map(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("cartItemId", item.getId());
            map.put("productId", item.getProduct().getId());
            map.put("name", item.getProduct().getName());
            map.put("price", item.getProduct().getPrice());
            byte[] img = item.getProduct().getImage();
            map.put("image", (img != null && img.length > 0) 
                            ? "data:image/png;base64," + Base64.getEncoder().encodeToString(img)
                            : "");
            return map;
        }).collect(Collectors.toList());
    }

    // ---------------- Remove item from cart ----------------
    @PostMapping("/remove/{cartItemId}")
    @ResponseBody
    public Map<String, Object> removeCartItem(@PathVariable("cartItemId") Integer cartItemId,
                                              HttpSession session) {
        UserRegisterEntity customer = (UserRegisterEntity) session.getAttribute("customer");
        if (customer == null) {
            return Map.of("status", "error", "message", "Login required");
        }

        cartService.removeCartItem(cartItemId);

        return Map.of("status", "removed");
    }
    
    
}
