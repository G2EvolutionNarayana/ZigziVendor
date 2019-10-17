package g2evolution.Boutique.entit;


import java.util.ArrayList;

/**
 * Created by G2evolution on 5/20/2017.
 */

public class Entity_CategorySimilarDataModel {



    private String headerid;
    private String headerTitle;
    private ArrayList<Entoty_CategorySingleItemModel> allItemsInSection;


    public Entity_CategorySimilarDataModel() {

    }

    public Entity_CategorySimilarDataModel(String headerid, String headerTitle, ArrayList<Entoty_CategorySingleItemModel> allItemsInSection) {
        this.headerTitle = headerid;
        this.headerTitle = headerTitle;
        this.allItemsInSection = allItemsInSection;
    }

    public String getHeaderid() {
        return headerid;
    }

    public void setHeaderid(String headerid) {
        this.headerid = headerid;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<Entoty_CategorySingleItemModel> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<Entoty_CategorySingleItemModel> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }


}