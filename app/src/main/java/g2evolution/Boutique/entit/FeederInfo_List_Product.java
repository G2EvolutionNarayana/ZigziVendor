package g2evolution.Boutique.entit;

import java.util.Map;

public class FeederInfo_List_Product {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getElectronicname() {
        return electronicname;
    }

    public void setElectronicname(String electronicname) {
        this.electronicname = electronicname;
    }

    public String getElectronicdetail1() {
        return electronicdetail1;
    }

    public void setElectronicdetail1(String electronicdetail1) {
        this.electronicdetail1 = electronicdetail1;
    }

    public String getElectronicdetail2() {
        return electronicdetail2;
    }

    public void setElectronicdetail2(String electronicdetail2) {
        this.electronicdetail2 = electronicdetail2;
    }

    public String getElectronicprice() {
        return electronicprice;
    }

    public void setElectronicprice(String electronicprice) {
        this.electronicprice = electronicprice;
    }

    public String getElectronicimage() {
        return electronicimage;
    }

    public void setElectronicimage(String electronicimage) {
        this.electronicimage = electronicimage;
    }

    public String getDiscountvalue() {
        return discountvalue;
    }

    public void setDiscountvalue(String discountvalue) {
        this.discountvalue = discountvalue;
    }

    public String getAfterdiscount() {
        return afterdiscount;
    }

    public void setAfterdiscount(String afterdiscount) {
        this.afterdiscount = afterdiscount;
    }

    public String getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(String stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    private String id;
    private String categoryname;
    private String electronicname;
    private String electronicdetail1;
    private String electronicdetail2;
    private String electronicprice;
    private String electronicimage;
    private String discountvalue;
    private String afterdiscount;
    private String stockQuantity;
    private Map<String,String> mapparameters;


    public Map<String, String> getMapparameters() {
        return mapparameters;
    }

    public void setMapparameters(Map<String, String> mapparameters) {
        this.mapparameters = mapparameters;
    }
}

