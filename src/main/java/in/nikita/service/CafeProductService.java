package in.nikita.service;

import java.util.List;

import in.nikita.entity.CafePruduct;
import in.nikita.entity.Cafe_Categories;
import in.nikita.entity.ProductStock;

public interface CafeProductService {
    CafePruduct saveCafeProduct(CafePruduct product);
    List<CafePruduct> getAllCafeProduct();
    CafePruduct getProductById(int id);
    void deleteCafeProduct(int id);
    CafePruduct addProduct(String name, String description, double price, int categoryId, int stockId,
                           byte[] image);

}
