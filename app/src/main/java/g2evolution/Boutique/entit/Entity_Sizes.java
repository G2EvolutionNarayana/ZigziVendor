package g2evolution.Boutique.entit;

public class Entity_Sizes {

    String Id;
    String productName;
    Integer ProductImage;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductImage() {
        return ProductImage;
    }

    public void setProductImage(Integer productImage) {
        ProductImage = productImage;
    }
}
