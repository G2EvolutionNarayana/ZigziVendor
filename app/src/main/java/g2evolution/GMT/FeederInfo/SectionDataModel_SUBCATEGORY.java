package g2evolution.GMT.FeederInfo;


import java.util.ArrayList;

/**
 * Created by G2evolution on 5/20/2017.
 */

public class SectionDataModel_SUBCATEGORY {



    private String headerTitle;
    private  String headerid;

    public String getHeaderid() {
        return headerid;
    }

    public void setHeaderid(String headerid) {
        this.headerid = headerid;
    }

    public SectionDataModel_SUBCATEGORY(String headerTitle, String headerid, ArrayList<SingleItemModel_SUBCATEGORY> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.headerid = headerid;
        this.allItemsInSection = allItemsInSection;
    }

    private ArrayList<SingleItemModel_SUBCATEGORY> allItemsInSection;


    public SectionDataModel_SUBCATEGORY() {

    }



    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<SingleItemModel_SUBCATEGORY> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<SingleItemModel_SUBCATEGORY> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }

}