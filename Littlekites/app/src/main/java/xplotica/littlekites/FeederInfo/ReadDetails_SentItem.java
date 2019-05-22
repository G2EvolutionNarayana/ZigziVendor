package xplotica.littlekites.FeederInfo;

/**
 * Created by Santanu on 11-04-2017.
 */

public class ReadDetails_SentItem {

    String Title;

    String Description;

    public ReadDetails_SentItem(String title, String description) {
        Title = title;
        Description = description;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
