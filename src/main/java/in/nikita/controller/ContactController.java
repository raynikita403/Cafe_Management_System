package in.nikita.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.nikita.service.EmailService;

@Controller
public class ContactController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/contact")
    public String sendContactForm(@RequestParam String name,
                                  @RequestParam String email,
                                  @RequestParam String phone,
                                  @RequestParam String message,
                                  RedirectAttributes redirectAttributes) {
        try {
            emailService.sendContactMessage(name, email, message);
            redirectAttributes.addFlashAttribute("success", "Message sent successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to send message. Try again!");
            e.printStackTrace();
        }
        return "customer/User";
    }
}
