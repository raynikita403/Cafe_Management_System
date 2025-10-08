package in.nikita.service;

import java.util.List;
import java.util.Map;

import in.nikita.entity.Orders;
import in.nikita.entity.UserRegisterEntity;

public interface OrderService {
    boolean placeOrders(UserRegisterEntity user, List<Map<String, Object>> orderItems);
    List<Orders> getAllOrders();

}
