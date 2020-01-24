package g2evolution.Boutique.entit;

import java.util.ArrayList;

public class Entity_descriptionheader {


    private String headerTitle;
    private String headervalue;
    private ArrayList<Entity_descriptionchild> allItemsInSection;


    public Entity_descriptionheader() {

    }
    public Entity_descriptionheader(String headerTitle, String headervalue, ArrayList<Entity_descriptionchild> allItemsInSection) {
        this.headerTitle = headerTitle;
        this.headervalue = headervalue;
        this.allItemsInSection = allItemsInSection;
    }


    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<Entity_descriptionchild> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<Entity_descriptionchild> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }

    public String getHeadervalue() {
        return headervalue;
    }

    public void setHeadervalue(String headervalue) {
        this.headervalue = headervalue;
    }
}
