package g2evolution.Boutique.entit;

import java.util.ArrayList;

public class Entity_weightchild {


    private String headercode;
    private String name;
    private String value;
    private String isselected;


    public Entity_weightchild() {
    }

    public Entity_weightchild(String headercode,String name,String value, String isselected) {

        this.headercode = headercode;
        this.name = name;
        this.value = value;
        this.isselected = isselected;
    }

    public String getHeadercode() {
        return headercode;
    }

    public void setHeadercode(String headercode) {
        this.headercode = headercode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIsselected() {
        return isselected;
    }

    public void setIsselected(String isselected) {
        this.isselected = isselected;
    }
}
