package xplotica.littlekites.FeederInfo;

/**
 * Created by santa on 3/1/2017.
 */
public class History_diary_feederInfo {

    private String _date;
    private String _topic_details;
    private String dairyid;

    public String getDairyid() {
        return dairyid;
    }

    public void setDairyid(String dairyid) {
        this.dairyid = dairyid;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public String get_topic_details() {
        return _topic_details;
    }

    public void set_topic_details(String _topic_details) {
        this._topic_details = _topic_details;
    }
}
