package in.nikita.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.nikita.entity.CafePruduct;
import in.nikita.entity.Cafe_Categories;
import in.nikita.entity.ProductStock;
import in.nikita.repository.CafeProductRepository;
import in.nikita.repository.Cafe_CategoriesRepository;
import in.nikita.repository.ProductStockRepository;

@Service
public class CafeProductServiceImpli implements CafeProductService {

    @Autowired 
    private CafeProductRepository repo;

    @Autowired
    private Cafe_CategoriesRepository categoryRepo;

    @Autowired
    private ProductStockRepository stockRepo;

    @Override
    public CafePruduct saveCafeProduct(CafePruduct product) {
        return repo.save(product);
    }

    @Override
    public List<CafePruduct> getAllCafeProduct() {
        return repo.findAll();
    }

    @Override
    public CafePruduct getProductById(int id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public void deleteCafeProduct(int id) {
        repo.deleteById(id);
    }

    @Override
    public CafePruduct addProduct(String name, String description, double price, int categoryId, int stockId, byte[] image) {
        CafePruduct p = new CafePruduct();
        p.setName(name);
        p.setDescription(description);
        p.setPrice(price);
        p.setImage(image); // store image bytes directly

        Cafe_Categories category = categoryRepo.findById(categoryId).orElse(null);
        ProductStock stock = stockRepo.findById(stockId).orElse(null);

        p.setCategory(category);
        p.setStockStatus(stock);

        return repo.save(p);
    }
}
