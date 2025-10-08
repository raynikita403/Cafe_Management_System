package in.nikita.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import in.nikita.entity.Orders;
import in.nikita.entity.UserRegisterEntity;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    // Get orders by a specific user
    List<Orders> findByUser(UserRegisterEntity user);
    
 // Yearly
    @Query("SELECT o.product.name, SUM(o.productQty) as totalQty " +
           "FROM Orders o " +
           "WHERE YEAR(o.createdAt) = :year " +
           "GROUP BY o.product.name " +
           "ORDER BY totalQty DESC")
    List<Object[]> findTopSellingProductsByYear(@Param("year") int year);

    // Monthly
    @Query("SELECT o.product.name, SUM(o.productQty) as totalQty " +
           "FROM Orders o " +
           "WHERE YEAR(o.createdAt) = :year AND MONTH(o.createdAt) = :month " +
           "GROUP BY o.product.name " +
           "ORDER BY totalQty DESC")
    List<Object[]> findTopSellingProductsByMonth(@Param("year") int year, @Param("month") int month);

    // Weekly 
    @Query("SELECT o.product.name, SUM(o.productQty) as totalQty " +
    	       "FROM Orders o " +
    	       "WHERE o.createdAt BETWEEN :start AND :end " +
    	       "GROUP BY o.product.name " +
    	       "ORDER BY totalQty DESC")
    	List<Object[]> findTopSellingProductsByWeek(@Param("start") java.time.LocalDateTime start,
    	                                            @Param("end") java.time.LocalDateTime end);



  }
