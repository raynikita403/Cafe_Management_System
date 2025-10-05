package in.nikita.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "orders") 
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private CafePruduct product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserRegisterEntity user;

    private int productQty;
    private double bill;

    public Orders() {}

    public Orders(CafePruduct product, UserRegisterEntity user, int productQty, double bill) {
        this.product = product;
        this.user = user;
        this.productQty = productQty;
        this.bill = bill;
    }

    // Getters and Setters
    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public CafePruduct getProduct() { return product; }
    public void setProduct(CafePruduct product) { this.product = product; }

    public UserRegisterEntity getUser() { return user; }
    public void setUser(UserRegisterEntity user) { this.user = user; }

    public int getProductQty() { return productQty; }
    public void setProductQty(int productQty) { this.productQty = productQty; }

    public double getBill() { return bill; }
    public void setBill(double bill) { this.bill = bill; }

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", product=" + product + ", user=" + user 
                + ", productQty=" + productQty + ", bill=" + bill + "]";
    }
}
