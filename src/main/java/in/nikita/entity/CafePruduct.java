package in.nikita.entity;

import jakarta.persistence.*;

@Entity
public class CafePruduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    private double price;

    @Lob
    @Column(name = "image", columnDefinition = "BLOB")
    private byte[] image; // Store image bytes directly

    @ManyToOne
    @JoinColumn(name = "category_id") 
    private Cafe_Categories category;

    @ManyToOne
    @JoinColumn(name = "stock_status_id") 
    private ProductStock stockStatus;

    public CafePruduct() {}

    public CafePruduct(Integer id, String name, String description, double price, byte[] image,
                       Cafe_Categories category, ProductStock stockStatus) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
        this.category = category;
        this.stockStatus = stockStatus;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }

    public Cafe_Categories getCategory() { return category; }
    public void setCategory(Cafe_Categories category) { this.category = category; }

    public ProductStock getStockStatus() { return stockStatus; }
    public void setStockStatus(ProductStock stockStatus) { this.stockStatus = stockStatus; }

    @Override
    public String toString() {
        return "CafePruduct [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
                + ", category=" + category + ", stockStatus=" + stockStatus + "]";
    }
}
