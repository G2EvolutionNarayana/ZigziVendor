package xplotica.littlekites.FeederInfo_parent;

import java.util.Date;

/**
 * Created by Santanu on 03-04-2017.
 */

public class parent_Events_feederInfo {


    private String _eventnaem;
String id;
    private String _message;
    private String _strdate;
    private String _enddate;
    Date date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String get_eventnaem() {
        return _eventnaem;
    }

    public void set_eventnaem(String _eventnaem) {
        this._eventnaem = _eventnaem;
    }


    public String get_message() {
        return _message;
    }

    public void set_message(String _message) {
        this._message = _message;
    }

    public String get_strdate() {
        return _strdate;
    }

    public void set_strdate(String _strdate) {
        this._strdate = _strdate;
    }

    public String get_enddate() {
        return _enddate;
    }

    public void set_enddate(String _enddate) {
        this._enddate = _enddate;
    }
}
