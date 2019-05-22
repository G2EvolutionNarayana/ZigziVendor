package g2evolution.GMT.FeederInfo;


import java.util.ArrayList;

/**
 * Created by G2evolution on 5/20/2017.
 */

public class SimilarDataModel {



    private String headerTitle;
    private ArrayList<SingleItemModel> allItemsInSection;


    public SimilarDataModel() {

    }
    public SimilarDataModel(String headerTitle, ArrayList<SingleItemModel> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.allItemsInSection = allItemsInSection;
    }



    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<SingleItemModel> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<SingleItemModel> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }


}