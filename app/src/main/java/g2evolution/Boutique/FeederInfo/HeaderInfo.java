package g2evolution.Boutique.FeederInfo;

/**
 * Created by G2Evolution on 5/26/2016.
 */
import java.util.ArrayList;

public class HeaderInfo {

    private String name;
    private String image;
    private String textchild;

    public String getTextchild() {
        return textchild;
    }

    public void setTextchild(String textchild) {
        this.textchild = textchild;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private ArrayList<DetailInfo> productList = new ArrayList<DetailInfo>();;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public ArrayList<DetailInfo> getProductList() {
        return productList;
    }
    public void setProductList(ArrayList<DetailInfo> productList) {
        this.productList = productList;
    }

}