package in.nikita.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.nikita.entity.CafePruduct;
import in.nikita.entity.Cafe_Categories;
import in.nikita.entity.ProductStock;
import in.nikita.entity.UserRegisterEntity;
import in.nikita.service.Cafe_CategoriesService;
import in.nikita.service.ProductStockService;
import in.nikita.service.UserRegisterService;

import jakarta.servlet.http.HttpSession;

@Controller
public class RegisterLoginController {

    @Autowired
    private UserRegisterService userService;

    @Autowired
    private Cafe_CategoriesService categoryService;

    @Autowired
    private ProductStockService stockService;

    // ---------------- Registration Page ----------------
    @GetMapping("/index")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegisterEntity());
        return "index";
    }

    // ---------------- Handle Registration ----------------
    @PostMapping("/index")
    public String registerUser(@ModelAttribute("user") UserRegisterEntity user, Model model) {
        if (userService.emailExists(user.getUserEmail())) {
            model.addAttribute("error", "Email already exists!");
            return "index";
        }
        userService.registerUser(user);
        model.addAttribute("success", "Registration successful! You can login now.");
        return "login";
    }

    // ---------------- Login Page ----------------
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new UserRegisterEntity());
        return "login";
    }

    // ---------------- Handle Login ----------------
    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") UserRegisterEntity loginUser,
                            Model model, HttpSession session) {

        UserRegisterEntity user = userService.loginUser(
                loginUser.getUserEmail(),
                loginUser.getUserPassword()
        );

        if (user != null) {
        	  // ✅ Add the full customer object for order mapping later
            session.setAttribute("customer", user);
            
            session.setAttribute("token", user.getToken());
            session.setAttribute("userName", user.getUserName());
            session.setAttribute("userRole", user.getRole());
            session.setAttribute("username", user.getUserEmail());
            
            // Manually set authentication in SecurityContext for session
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    user.getUserEmail(),
                    null,
                    Collections.singletonList(new SimpleGrantedAuthority(user.getRole()))
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            // Redirect based on role
            if ("ROLE_ADMIN".equals(user.getRole())) return "redirect:/Admin";
            else return "redirect:/Customer";
        } else {
            model.addAttribute("error", "Invalid email or password!");
            return "login";
        }
    }

    // ---------------- Admin Dashboard ----------------
    @GetMapping("/Admin")
    public String adminDash(Model model) {
        // Add empty product for modal form binding
        model.addAttribute("product", new CafePruduct());

        // Add categories and stocks for modal dropdowns
        List<Cafe_Categories> categories = categoryService.getAllCategories();
        List<ProductStock> stocks = stockService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("stocks", stocks);

        return "admin/Admin"; 
    }

    // ---------------- Customer Dashboard ----------------
    @GetMapping("/Customer")
    public String userDash() {
        return "customer/User";
    }
}
