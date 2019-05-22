package xplotica.littlekites.FeederInfo;

/**
 * Created by Sujata on 07-11-2016.
 */
public class ItemObject_home {

    private String name;
    private int photo;
    private int backcolor;

   /* public ItemObject_home(String name,int photo,String backcolor){
        this.name=name;
        this.photo=photo;
        this.backcolor=backcolor;

    }*/

    public int getBackcolor() {
        return backcolor;
    }

    public void setBackcolor(int backcolor) {
        this.backcolor = backcolor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
