package in.nikita.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.nikita.entity.CafePruduct;
import in.nikita.entity.Orders;
import in.nikita.entity.UserRegisterEntity;
import in.nikita.repository.CafeProductRepository;
import in.nikita.repository.CartItemrepository;
import in.nikita.repository.OrdersRepository;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersRepository orderRepo;

    @Autowired
    private CartItemrepository cartRepo;

    @Autowired
    private CafeProductRepository productRepo;

    @Transactional
    @Override
    public boolean placeOrders(UserRegisterEntity user, List<Map<String, Object>> orderItems) {
        try {
            for (Map<String, Object> item : orderItems) {
                Integer productId = (Integer) item.get("productId");
                Integer qty = (Integer) item.get("quantity");

                CafePruduct product = productRepo.findById(productId).orElse(null);
                if(product == null) continue;

                double bill = product.getPrice() * qty;

                // Save to Orders table
                Orders order = new Orders(product, user, qty, bill);
                orderRepo.save(order);

                // Remove from Cart
                cartRepo.deleteByCustomerAndProduct(user, product);
            }
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
