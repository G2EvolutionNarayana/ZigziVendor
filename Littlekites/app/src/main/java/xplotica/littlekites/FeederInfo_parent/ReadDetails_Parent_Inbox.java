package xplotica.littlekites.FeederInfo_parent;

/**
 * Created by Santanu on 10-04-2017.
 */

public class ReadDetails_Parent_Inbox {

    String Title;

    String Description;

    public ReadDetails_Parent_Inbox(String title, String description) {
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
