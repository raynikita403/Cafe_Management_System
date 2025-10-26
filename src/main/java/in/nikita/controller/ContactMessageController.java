package in.nikita.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import in.nikita.entity.ContactMessage;
import in.nikita.service.ContactMessageService;

import java.util.List;

@Controller
public class ContactMessageController {

    @Autowired
    private ContactMessageService service;

    //Show all messages (admin side)
    
    @GetMapping("/messages")
    public String showMessages(Model model) {
        List<ContactMessage> messages = service.displayMessage();
        messages.sort((a, b) -> b.getTimeDate().compareTo(a.getTimeDate()));
        model.addAttribute("messages", messages);
        return "/admin/Messages :: messageList";
    }

    // Save message (user side form)
    @PostMapping("/saveMessage")
    @ResponseBody
    public String saveMessage(@RequestParam String name,
                              @RequestParam String email,
                              @RequestParam String message) {

        ContactMessage msg = new ContactMessage();
        msg.setName(name);
        msg.setEmail(email);
        msg.setMessage(message);

        service.addMessage(msg);
        return "success";
    }
}
