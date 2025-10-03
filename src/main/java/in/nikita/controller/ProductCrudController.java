package in.nikita.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import in.nikita.entity.CafePruduct;
import in.nikita.entity.Cafe_Categories;
import in.nikita.entity.ProductStock;
import in.nikita.service.CafeProductService;
import in.nikita.service.Cafe_CategoriesService;
import in.nikita.service.ProductStockService;

@Controller
public class ProductCrudController {

    @Autowired
    private CafeProductService productService;

    @Autowired
    private Cafe_CategoriesService categoryService;

    @Autowired
    private ProductStockService stockService;

    // ---------------- Show Products Page ----------------
    @GetMapping("/productsList")
    public String showProducts(Model model) {
    	 System.out.println("DEBUG: /productsList called");
    	    model.addAttribute("products", productService.getAllCafeProduct());
    	    System.out.println("Products size:tell me " + productService.getAllCafeProduct().size());
    	    return "admin/Products :: productListFragment";
        
    }


    // ---------------- Handle Product Save ----------------
    @PostMapping("/saveProduct")
    public String saveProduct(@RequestParam("name") String name,
                              @RequestParam("description") String description,
                              @RequestParam("price") double price,
                              @RequestParam("categoryId") int categoryId,
                              @RequestParam("stockId") int stockId,
                              @RequestParam("productImage") MultipartFile file,
                              Model model) throws IOException {

        CafePruduct product = new CafePruduct();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);

        // Set Category & Stock
        Cafe_Categories category = categoryService.getCategoryById(categoryId);
        ProductStock stock = stockService.getProductStockId(stockId);
        product.setCategory(category);
        product.setStockStatus(stock);

        // Set Image
        if (!file.isEmpty()) {
            product.setImage(file.getBytes());
        }

        productService.saveCafeProduct(product);

        // Reload data for full Admin page
        model.addAttribute("products", productService.getAllCafeProduct());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("stocks", stockService.getAllProductStocks());
        model.addAttribute("product", new CafePruduct());

        return "admin/Admin";
    }
 
    //----------For Update ----------------------
    
    
    @GetMapping("/editProduct/{id}")
    public String editProductForm(@PathVariable int id, Model model) {
        CafePruduct product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("stocks", stockService.getAllProductStocks());
        return "admin/EditProductForm :: editProductForm";
    }
    @PostMapping("/updateProduct")
    @ResponseBody
    public ResponseEntity<?> updateProduct(@RequestParam("id") int id,
                                           @RequestParam("name") String name,
                                           @RequestParam("description") String description,
                                           @RequestParam("price") double price,
                                           @RequestParam("categoryId") int categoryId,
                                           @RequestParam("stockId") int stockId,
                                           @RequestParam(value = "productImage", required = false) MultipartFile file) throws IOException {

        // Fetch existing product
        CafePruduct product = productService.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build(); // Return 404 if product not found
        }

        // Update fields
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setCategory(categoryService.getCategoryById(categoryId));
        product.setStockStatus(stockService.getProductStockId(stockId));

        // Update image if provided
        if (file != null && !file.isEmpty()) {
            product.setImage(file.getBytes());
        }

        // Save updated product
        productService.saveCafeProduct(product);
        return ResponseEntity.ok("{\"status\":\"success\"}");
    }



    // ---------------- Delete Product ----------------
    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable int id, Model model) {
        productService.deleteCafeProduct(id);
        model.addAttribute("products", productService.getAllCafeProduct());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("stocks", stockService.getAllProductStocks());
        model.addAttribute("product", new CafePruduct());
        return "admin/Admin"; 
    }

    // ---------------- Serve Product Image ----------------
    @GetMapping("/product-images/{id}")
    public ResponseEntity<byte[]> serveProductImage(@PathVariable int id) {
        CafePruduct product = productService.getProductById(id);
        if (product != null && product.getImage() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(product.getImage());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
