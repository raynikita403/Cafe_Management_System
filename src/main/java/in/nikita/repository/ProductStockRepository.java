package in.nikita.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nikita.entity.ProductStock;

public interface ProductStockRepository extends JpaRepository<ProductStock, Integer>{

}
