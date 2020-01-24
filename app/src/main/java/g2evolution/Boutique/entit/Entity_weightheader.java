package g2evolution.Boutique.entit;

import java.util.ArrayList;

public class Entity_weightheader {


    private String headerTitle;
    private String headervalue;
    private ArrayList<Entity_weightchild> allItemsInSection;


    public Entity_weightheader() {

    }
    public Entity_weightheader(String headerTitle, String headervalue,ArrayList<Entity_weightchild> allItemsInSection) {
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

    public ArrayList<Entity_weightchild> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<Entity_weightchild> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }

    public String getHeadervalue() {
        return headervalue;
    }

    public void setHeadervalue(String headervalue) {
        this.headervalue = headervalue;
    }
}
