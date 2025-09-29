package in.nikita.service;

import java.util.List;

import in.nikita.entity.Cafe_Categories;
import in.nikita.entity.ProductStock;

public interface ProductStockService {
	ProductStock saveCategories(ProductStock stock);
	List<ProductStock>getAllCategories();
	ProductStock getProductStockId(int stockId);
	Object getAllProductStocks();

}
