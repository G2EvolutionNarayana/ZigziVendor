package g2evolution.Boutique.entit;

public class Entity_Generalinfo {

    String Id;
    String productName;
    String productValue;
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

    public String getProductValue() {
        return productValue;
    }

    public void setProductValue(String productValue) {
        this.productValue = productValue;
    }

    public Integer getProductImage() {
        return ProductImage;
    }

    public void setProductImage(Integer productImage) {
        ProductImage = productImage;
    }
}
