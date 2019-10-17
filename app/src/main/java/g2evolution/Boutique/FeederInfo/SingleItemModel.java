package g2evolution.Boutique.FeederInfo;

/**
 * Created by G2evolution on 5/20/2017.
 */

public class SingleItemModel {


    private String id;
    private String categoryName;
    private String name;
    private String url;
    private String description;
    private String subcatname;
    private String stockQuantity;

    public String getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(String stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getSubcatname() {
        return subcatname;
    }

    public void setSubcatname(String subcatname) {
        this.subcatname = subcatname;
    }

    private String disper;

    public String getProductdetails() {
        return productdetails;
    }

    public void setProductdetails(String productdetails) {
        this.productdetails = productdetails;
    }

    public String getPhoprice() {
        return phoprice;
    }

    public void setPhoprice(String phoprice) {
        this.phoprice = phoprice;
    }


    private String productdetails;
    private String phoprice;
    private String discprice;
    private String discvalue;


    public SingleItemModel(String id, String categoryName , String name, String productdetails , String phoprice, String discvalue , String discprice , String url,String stockQuantity ) {
        this.id = id;
        this.categoryName = categoryName;
        this.name = name;
        this.url = url;
        this.stockQuantity = stockQuantity;

        this.productdetails = productdetails;
        this.phoprice = phoprice;
        this.discprice = discprice;
        this.discvalue = discvalue;
        this.subcatname = subcatname;

    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDiscvalue() {
        return discvalue;
    }

    public void setDiscvalue(String discvalue) {
        this.discvalue = discvalue;
    }

    public String getDiscprice() {
        return discprice;
    }

    public void setDiscprice(String discprice) {
        this.discprice = discprice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}