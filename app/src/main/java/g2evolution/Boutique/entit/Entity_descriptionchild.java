package g2evolution.Boutique.entit;

public class Entity_descriptionchild {


    private String name;
    private String value;


    public Entity_descriptionchild() {
    }

    public Entity_descriptionchild(String name, String value) {

        this.name = name;
        this.value = value;
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

}
