package in.nikita.entity;

import jakarta.persistence.*;

@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserRegisterEntity customer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private CafePruduct product;


    public CartItem() {}

    public CartItem(UserRegisterEntity customer, CafePruduct product) {
        this.customer = customer;
        this.product = product;
    }

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public UserRegisterEntity getCustomer() { return customer; }
    public void setCustomer(UserRegisterEntity customer) { this.customer = customer; }

    public CafePruduct getProduct() { return product; }
    public void setProduct(CafePruduct product) { this.product = product; }

	@Override
	public String toString() {
		return "CartItem [id=" + id + ", customer=" + customer + ", product=" + product + "]";
	}

   
}
