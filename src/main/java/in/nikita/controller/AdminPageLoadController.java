package in.nikita.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageLoadController {
	
    //Product.html Fetching data from db present in ProductCrudController

    //for Orders button
    @GetMapping("/orders")
    public String orders() {
        return "/admin/Orders";
    }
	
    //for Charts button
    @GetMapping("/charts")
    public String charts() {
        return "/admin/Charts";
    }
		
    //for Manage Users button
    @GetMapping("/manageUsers")
    public String manageUsers() {
        return "/admin/ManageUsers";
    }
}
