package g2evolution.GMT.FeederInfo;

/**
 * Created by G2evolution on 5/20/2017.
 */

public class SingleItemModel_SUBCATEGORY {

    private String name;
    private String url;
    private String description;


    public SingleItemModel_SUBCATEGORY() {
    }

    public SingleItemModel_SUBCATEGORY(String name, String url) {
        this.name = name;
        this.url = url;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}