package in.nikita.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.nikita.entity.CafePruduct;
import in.nikita.entity.CartItem;
import in.nikita.entity.UserRegisterEntity;
import in.nikita.repository.CartItemrepository;

@Service
public class CartServiceImpli implements CartService {
	@Autowired
    private CartItemrepository cartRepo;
	
	@Override
	public CartItem addToCart(UserRegisterEntity customer, CafePruduct product) {
		  CartItem existing = cartRepo.findByCustomerAndProduct(customer, product);
		  CartItem item = new CartItem(customer, product);
	        return cartRepo.save(item);
	}

	@Override
	public List<CartItem> getCartItems(UserRegisterEntity customer) {
		  return cartRepo.findByCustomer(customer);
		
	}

	@Override
	public void removeCartItem(int cartItemId) {
		 cartRepo.deleteById(cartItemId);
		
	}

	@Override
	public void clearCart(UserRegisterEntity customer) {
		  List<CartItem> items = cartRepo.findByCustomer(customer);
	        cartRepo.deleteAll(items);
		
	}

	@Override
	public void removeCartItemByProduct(UserRegisterEntity customer, Integer productId) {
		// Find CartItem by customer and product
	    List<CartItem> items = cartRepo.findByCustomer(customer);
	    for (CartItem item : items) {
	        if (item.getProduct().getId().equals(productId)) {
	            cartRepo.delete(item);
	            break; // remove only the first matching item
	        }
	    }
		
	}

}
