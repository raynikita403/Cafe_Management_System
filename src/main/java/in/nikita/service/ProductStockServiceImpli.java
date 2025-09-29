package in.nikita.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.nikita.entity.ProductStock;
import in.nikita.repository.ProductStockRepository;
@Service
public class ProductStockServiceImpli implements ProductStockService{
	@Autowired
	ProductStockRepository repo;
	@Override
	public ProductStock saveCategories(ProductStock stock) {
		return repo.save(stock);
	}

	@Override
	public List<ProductStock> getAllCategories() {
		return repo.findAll();
	}

	@Override
	public ProductStock getProductStockId(int stockId) {
		return repo.getById(stockId);
	}

	@Override
	public Object getAllProductStocks() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

}
