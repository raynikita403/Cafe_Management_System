package in.nikita.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import in.nikita.entity.Orders;
import in.nikita.entity.UserRegisterEntity;
import in.nikita.service.OrderService;
import in.nikita.service.UserRegisterService;

@Controller
public class AdminPageLoadController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRegisterService userService;

    // ---------------- Orders ----------------
    @GetMapping("/orders")
    public String orders(Model model) {
        List<Orders> orders = orderService.getAllOrders();
        orders.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
        model.addAttribute("orders", orders);
        return "/admin/Orders :: ordersListFragment";
    }

    // ---------------- Charts ----------------
    @GetMapping("/charts")
    public String charts() {
        return "/admin/Charts";
    }

    // ---------------- Manage Users ----------------
    @GetMapping("/manageUsers")
    public String manageUsersFragment(Model model) {
        List<UserRegisterEntity> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "/admin/ManageUsers :: manageUsersFragment";
    }

    // ---------------- Toggle User Active/Inactive ----------------
    @PostMapping("/admin/toggleUserActive/{id}")
    public String toggleUserActive(@PathVariable("id") Integer id, Model model) {
        userService.toggleUserActive(id);
        model.addAttribute("users", userService.getAllUsers());

        // Return fragment only for AJAX
        return "/admin/ManageUsers :: manageUsersFragment";
    }
    
   

}
