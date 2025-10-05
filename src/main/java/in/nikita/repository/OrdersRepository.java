package in.nikita.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nikita.entity.Orders;

public interface OrdersRepository extends JpaRepository<Orders,Integer> {

}
