package in.nikita.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.nikita.entity.CafePruduct;
import in.nikita.entity.CartItem;
import in.nikita.entity.UserRegisterEntity;

@Repository
public interface CartItemrepository extends JpaRepository<CartItem, Integer>{

	List<CartItem> findByCustomer(UserRegisterEntity customer);

	CartItem findByCustomerAndProduct(UserRegisterEntity customer, CafePruduct product);
	 // Add method to delete all cart items of a customer
    void deleteByCustomer(UserRegisterEntity customer);

	void deleteByCustomerAndProduct(UserRegisterEntity user, CafePruduct product);

}
