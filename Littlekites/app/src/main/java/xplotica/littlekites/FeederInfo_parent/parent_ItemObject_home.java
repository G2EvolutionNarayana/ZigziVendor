package xplotica.littlekites.FeederInfo_parent;

/**
 * Created by Sujata on 07-11-2016.
 */
public class parent_ItemObject_home {

    private String name;
    private String backcolor;
    private int photo;

    public parent_ItemObject_home(String name, int photo, String backcolor){
        this.name=name;
        this.photo=photo;
        this.backcolor=backcolor;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackcolor() {
        return backcolor;
    }

    public void setBackcolor(String backcolor) {
        this.backcolor = backcolor;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
