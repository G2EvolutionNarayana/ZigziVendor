package g2evolution.Boutique.entit;

public class Entity_Resourse {

    String id;
    String titlename;
    String textcount;
    String textdesc;
    String textprice;
    String images;
    String category_id;

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitlename() {
        return titlename;
    }

    public void setTitlename(String titlename) {
        this.titlename = titlename;
    }

    public String getTextcount() {
        return textcount;
    }

    public void setTextcount(String textcount) {
        this.textcount = textcount;
    }

    public String getTextdesc() {
        return textdesc;
    }

    public void setTextdesc(String textdesc) {
        this.textdesc = textdesc;
    }

    public String getTextprice() {
        return textprice;
    }

    public void setTextprice(String textprice) {
        this.textprice = textprice;
    }
}
