package in.nikita.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import in.nikita.entity.CafePruduct;
import in.nikita.service.CafeProductService;
import in.nikita.service.Cafe_CategoriesService;
import in.nikita.service.ProductStockService;

@Controller
public class CustomerPageController {
	 @Autowired
	    private CafeProductService productService;

	    @Autowired
	    private Cafe_CategoriesService categoryService;

	    @Autowired
	    private ProductStockService stockService;
	    
	    @GetMapping("/home")
	    public String homePage() {
	        return "/customer/User";
	    }
	    
	    @GetMapping("/about")
	    public String aboutPage() {
	        return "/customer/About";
	    }
	    @GetMapping("/menu")
	    public String menuPage(Model model) {
	        List<CafePruduct> products = productService.getAllCafeProduct();
	        model.addAttribute("products", products);
	        model.addAttribute("isAdmin", false); // Customer view
	        return "customer/Menu";
	    }


}
