package in.nikita.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ProductStock {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	private String status;
	public ProductStock() {
		super();
	}
	public ProductStock(Integer id, String status) {
		super();
		this.id = id;
		this.status = status;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "ProductStock [id=" + id + ", status=" + status + "]";
	}
	public ProductStock getProductStockId(int stockId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
