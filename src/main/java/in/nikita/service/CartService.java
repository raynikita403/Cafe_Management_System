package in.nikita.service;

import java.util.List;
import in.nikita.entity.CartItem;
import in.nikita.entity.UserRegisterEntity;
import in.nikita.entity.CafePruduct;

public interface CartService {
    CartItem addToCart(UserRegisterEntity customer, CafePruduct product);
    List<CartItem> getCartItems(UserRegisterEntity customer);
    void removeCartItem(int cartItemId);
    void clearCart(UserRegisterEntity customer);
	void removeCartItemByProduct(UserRegisterEntity customer, Integer productId);
}
