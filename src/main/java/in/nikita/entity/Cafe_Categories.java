package in.nikita.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Cafe_Categories {
	  	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer cId;
	    private String cName; 
	    
	    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	    private List<CafePruduct> products;

		public Cafe_Categories() {
			super();
		}

		public Cafe_Categories(Integer cId, String cName, List<CafePruduct> products) {
			super();
			this.cId = cId;
			this.cName = cName;
			this.products = products;
		}

		public Integer getcId() {
			return cId;
		}

		public void setcId(Integer cId) {
			this.cId = cId;
		}

		public String getcName() {
			return cName;
		}

		public void setcName(String cName) {
			this.cName = cName;
		}

		public List<CafePruduct> getProducts() {
			return products;
		}

		public void setProducts(List<CafePruduct> products) {
			this.products = products;
		}

		@Override
		public String toString() {
			return "Cafe_Categories [cId=" + cId + ", cName=" + cName + ", products=" + products + "]";
		}
		
}
