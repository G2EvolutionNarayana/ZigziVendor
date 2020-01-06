package g2evolution.Boutique.entit;

import java.io.Serializable;

public class Entity_CategorySelect implements Serializable {

    private String itemName;
    private String itemcount;
    private String itemid;
    private boolean isSelected;

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public Entity_CategorySelect(String itemid,String itemName,String itemcount, boolean isSelected) {
        this.itemid = itemid;
        this.itemName = itemName;
        this.itemcount = itemcount;
        this.isSelected = isSelected;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemcount() {
        return itemcount;
    }

    public void setItemcount(String itemcount) {
        this.itemcount = itemcount;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}