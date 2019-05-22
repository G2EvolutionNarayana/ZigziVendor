package g2evolution.GMT.FeederInfo;


import java.util.ArrayList;

/**
 * Created by G2evolution on 5/20/2017.
 */

public class SectionDataModel {



    private String headerTitle;
    private String headerid;
    private ArrayList<SingleItemModel> allItemsInSection;


    public SectionDataModel() {

    }
    public SectionDataModel(String headerid,String headerTitle, ArrayList<SingleItemModel> allItemsInSection) {
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

    public ArrayList<SingleItemModel> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<SingleItemModel> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }


}